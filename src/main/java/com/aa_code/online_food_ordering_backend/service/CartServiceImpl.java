package com.aa_code.online_food_ordering_backend.service;

import com.aa_code.online_food_ordering_backend.Model.Cart;
import com.aa_code.online_food_ordering_backend.Model.CartItem;
import com.aa_code.online_food_ordering_backend.Model.Food;
import com.aa_code.online_food_ordering_backend.Model.User;
import com.aa_code.online_food_ordering_backend.repository.CartItemRepo;
import com.aa_code.online_food_ordering_backend.repository.CartRepo;
import com.aa_code.online_food_ordering_backend.repository.FoodRepo;
import com.aa_code.online_food_ordering_backend.request.AddCartItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{

    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;
    private final UserService userService;
    private final FoodService foodService;

    @Autowired
    public CartServiceImpl(CartRepo cartRepo, CartItemRepo cartItemRepo, UserService userService, FoodService foodService) {
        this.cartRepo = cartRepo;
        this.cartItemRepo = cartItemRepo;
        this.userService = userService;
        this.foodService = foodService;
    }

    @Override
    public CartItem addItemToCart(AddCartItemRequest addCartItemRequest, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Food food = foodService.findFoodById(addCartItemRequest.getFoodId());

        Cart cart = cartRepo.findByCustomerId(user.getId());

        for (CartItem cartItem : cart.getItem()){
            if (cartItem.getFood().equals(food)){
                int newQuantity = cartItem.getQuantity() * addCartItemRequest.getQuantity();

                return updateCartItemQuantity(cartItem.getId(), newQuantity);
            }
        }
        CartItem newCartItem = new CartItem();
        newCartItem.setFood(food);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(addCartItemRequest.getQuantity());
        newCartItem.setIngredients(addCartItemRequest.getIngredients());
        newCartItem.setTotalPrice(addCartItemRequest.getQuantity() * food.getPrice());

        CartItem saveCartItem = cartItemRepo.save(newCartItem);

        cart.getItem().add(saveCartItem);
        return saveCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        Optional<CartItem> cartItemOptional = cartItemRepo.findById(cartItemId);
        if (cartItemOptional.isEmpty()){
            throw new Exception("Cart item not found!");
        }
        CartItem item = cartItemOptional.get();
        item.setQuantity(quantity);
        item.setTotalPrice(item.getFood().getPrice()*quantity);

        return cartItemRepo.save(item);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Cart cart = cartRepo.findByCustomerId(user.getId());

        Optional<CartItem> optionalCartItem = cartItemRepo.findById(cartItemId);
        if (optionalCartItem.isEmpty()){
            throw new Exception("Cart Item not found!");
        }

        CartItem item = optionalCartItem.get();

        cart.getItem().remove(item);

        return cartRepo.save(cart);
    }

    @Override
    public Long calculateCartTotals(Cart cart) throws Exception {
        Long total = 0L;

        for (CartItem cartItem : cart.getItem()){
            total+=cartItem.getFood().getPrice()*cartItem.getQuantity();
        }
        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        Optional<Cart> optionalCart = cartRepo.findById(id);
        if (optionalCart.isEmpty()){
            throw new Exception("Cart not found with id" + id);
        }

        return optionalCart.get();
    }

    @Override
    public Cart findCartByUserId(String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        return cartRepo.findByCustomerId(user.getId());
    }

    @Override
    public Cart clearCart(String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = findCartByUserId(jwt);
        cart.getItem().clear();

        return cartRepo.save(cart);
    }
}
