package com.aa_code.online_food_ordering_backend.controller;

import com.aa_code.online_food_ordering_backend.Model.Order;
import com.aa_code.online_food_ordering_backend.Model.User;
import com.aa_code.online_food_ordering_backend.service.OrderService;
import com.aa_code.online_food_ordering_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/admin")
public class AdminOrderController {

    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public AdminOrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping(path = "/order/restaurant/{id}")
    public ResponseEntity<List<Order>> getOrderHistory(@PathVariable Long id, @RequestParam(required = false) String orderStatus, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Order> orders = orderService.getRestaurantOrder(id, orderStatus);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PutMapping(path = "/order/{id}/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @PathVariable String orderStatus, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Order orders = orderService.updateOrder(id, orderStatus);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
