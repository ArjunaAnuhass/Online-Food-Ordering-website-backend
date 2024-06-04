package com.aa_code.online_food_ordering_backend.service;

import com.aa_code.online_food_ordering_backend.Model.Order;
import com.aa_code.online_food_ordering_backend.Model.User;
import com.aa_code.online_food_ordering_backend.request.OrderRequest;
import org.aspectj.weaver.ast.Or;

import java.util.List;

public interface OrderService {

    public Order createOrder(OrderRequest orderRequest, User user) throws Exception;

    public Order updateOrder(Long orderId, String orderStatus) throws Exception;

    public void cancelOrder(Long orderId) throws Exception;

    public List<Order> getUsersOrder(Long userId) throws Exception;

    public List<Order> getRestaurantOrder(Long restaurantId, String orderStatus) throws Exception;
    public Order findOrderById(Long orderId) throws Exception;
}
