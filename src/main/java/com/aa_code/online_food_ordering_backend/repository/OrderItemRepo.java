package com.aa_code.online_food_ordering_backend.repository;

import com.aa_code.online_food_ordering_backend.Model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {


}
