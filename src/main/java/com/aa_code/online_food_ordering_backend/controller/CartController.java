package com.aa_code.online_food_ordering_backend.controller;

import com.aa_code.online_food_ordering_backend.Model.Cart;
import com.aa_code.online_food_ordering_backend.Model.CartItem;
import com.aa_code.online_food_ordering_backend.Model.User;
import com.aa_code.online_food_ordering_backend.request.AddCartItemRequest;
import com.aa_code.online_food_ordering_backend.request.UpdateCartItemRequest;
import com.aa_code.online_food_ordering_backend.service.CartService;
import com.aa_code.online_food_ordering_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api")
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    @Autowired
    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @PutMapping(path = "/cart/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody AddCartItemRequest request, @RequestHeader("Authorization") String jwt) throws Exception {
        CartItem cartItem = cartService.addItemToCart(request, jwt);

        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @PutMapping(path = "/cart-item/update")
    public ResponseEntity<CartItem> updateCartItemQuantity(@RequestBody UpdateCartItemRequest request) throws Exception {
        CartItem cartItem = cartService.updateCartItemQuantity(request.getCartItemId(), request.getQuantity());

        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @DeleteMapping(path = "/cart-item/{id}/remove")
    public ResponseEntity<Cart> removeCartItem(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception {
        Cart cart = cartService.removeItemFromCart(id, jwt);

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping(path = "/cart/clear")
    public ResponseEntity<Cart> clearCart(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.clearCart(user.getId());

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping(path = "/cart")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.findCartByUserId(user.getId());

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
    

}
