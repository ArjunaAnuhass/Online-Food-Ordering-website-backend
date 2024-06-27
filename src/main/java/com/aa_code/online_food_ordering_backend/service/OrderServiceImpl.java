package com.aa_code.online_food_ordering_backend.service;

import com.aa_code.online_food_ordering_backend.Model.*;
import com.aa_code.online_food_ordering_backend.repository.*;
import com.aa_code.online_food_ordering_backend.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepo orderRepo;
    private final OrderItemRepo orderItemRepo;
    private final AddressRepo addressRepo;
    private final UserRepo userRepo;
    private final RestaurantRepo restaurantRepo;
    private final RestaurantService restaurantService;
    private final CartService cartService;

    @Autowired
    public OrderServiceImpl(OrderRepo orderRepo, OrderItemRepo orderItemRepo, AddressRepo addressRepo, UserRepo userRepo, RestaurantRepo restaurantRepo, RestaurantService restaurantService, CartService cartService) {
        this.orderRepo = orderRepo;
        this.orderItemRepo = orderItemRepo;
        this.addressRepo = addressRepo;
        this.userRepo = userRepo;
        this.restaurantRepo = restaurantRepo;
        this.restaurantService = restaurantService;
        this.cartService = cartService;
    }

    @Override
    public Order createOrder(OrderRequest orderRequest, User user) throws Exception {

        Address shippingAddress = orderRequest.getDeliveryAddress();

        Address savedAddress = addressRepo.save(shippingAddress);

        if (!user.getAddresses().contains(savedAddress)){
            user.getAddresses().add(savedAddress);
            userRepo.save(user);
        }

        Restaurant restaurant = restaurantService.findRestaurantById(orderRequest.getRestaurantId());

        Order createdOrder = new Order();
        createdOrder.setCustomer(user);
        createdOrder.setCreatedAt(new Date());
        createdOrder.setOrderStatus("PENDING");
        createdOrder.setDeliveryAddress(savedAddress);
        createdOrder.setRestaurant(restaurant);

        //create order item
        Cart cart = cartService.findCartByUserId(user.getId());

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getItem()){
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());

            OrderItem savedOrderItem = orderItemRepo.save(orderItem);
            orderItems.add(savedOrderItem);
        }

        createdOrder.setItems(orderItems);
        createdOrder.setTotalAmount(cart.getTotal());

        Order savedOrder = orderRepo.save(createdOrder);

        restaurant.getOrders().add(savedOrder);

        return createdOrder;
    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        Order order = findOrderById(orderId);
        if (orderStatus.equals("OUT_FOR_DELIVERY") || orderStatus.equals("DELIVERED") || orderStatus.equals("COMPLETED") || orderStatus.equals("PENDING")){
            order.setOrderStatus(orderStatus);

            return orderRepo.save(order);
        }
        throw new Exception("Please select a valid order status!");
    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {
        Order order = findOrderById(orderId);
        orderRepo.deleteById(orderId);
    }

    @Override
    public List<Order> getUsersOrder(Long userId) throws Exception {
        return orderRepo.findByCustomerId(userId);
    }

    @Override
    public List<Order> getRestaurantOrder(Long restaurantId, String orderStatus) throws Exception {
        List<Order> orders = orderRepo.findByRestaurantId(restaurantId);
        if (orderStatus!=null){
            orders = orders.stream().filter(order->order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
        }

        return orders;
    }

    @Override
    public Order findOrderById(Long orderId) throws Exception {
        Optional<Order> optionalOrder = orderRepo.findById(orderId);
        if (optionalOrder.isEmpty()){
            throw new Exception("Order Not found!");
        }
        return optionalOrder.get();
    }
}
