package com.aa_code.online_food_ordering_backend.controller;

import com.aa_code.online_food_ordering_backend.Model.CartItem;
import com.aa_code.online_food_ordering_backend.Model.Order;
import com.aa_code.online_food_ordering_backend.Model.User;
import com.aa_code.online_food_ordering_backend.request.OrderRequest;
import com.aa_code.online_food_ordering_backend.service.OrderService;
import com.aa_code.online_food_ordering_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping(path = "/order")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.createOrder(orderRequest, user);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping(path = "/order/user")
    public ResponseEntity<List<Order>> getOrderHistory(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Order> orders = orderService.getUsersOrder(user.getId());

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
