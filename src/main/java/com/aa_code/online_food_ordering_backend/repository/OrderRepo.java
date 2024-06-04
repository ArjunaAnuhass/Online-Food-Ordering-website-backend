package com.aa_code.online_food_ordering_backend.repository;

import com.aa_code.online_food_ordering_backend.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Long> {

    public List<Order> findByCustomerId(Long userId);

    public List<Order> findByRestaurantId(Long restaurantId);


}
