package com.aa_code.online_food_ordering_backend.repository;

import com.aa_code.online_food_ordering_backend.Model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepo extends JpaRepository<CartItem, Long> {
}
