package com.aa_code.online_food_ordering_backend.service;

import com.aa_code.online_food_ordering_backend.Model.Cart;
import com.aa_code.online_food_ordering_backend.Model.CartItem;
import com.aa_code.online_food_ordering_backend.request.AddCartItemRequest;

public interface CartService {

    public CartItem addItemToCart(AddCartItemRequest addCartItemRequest, String jwt) throws Exception;

    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception;
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception;
    public Long calculateCartTotals(Cart cart) throws Exception;
    public Cart findCartById(Long id) throws Exception;
    public Cart findCartByUserId(Long userId) throws Exception;
    public Cart clearCart(Long userId) throws Exception;
}
