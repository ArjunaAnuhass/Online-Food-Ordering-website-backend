package com.aa_code.online_food_ordering_backend.response;

import com.aa_code.online_food_ordering_backend.Model.Address;
import com.aa_code.online_food_ordering_backend.Model.OrderItem;
import com.aa_code.online_food_ordering_backend.Model.Restaurant;
import com.aa_code.online_food_ordering_backend.Model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderResponse {

    private Long id;
    private User customer;
    private Restaurant restaurant;

    private String orderStatus;
    private Date createdAt;
    private Address deliveryAddress;
}
