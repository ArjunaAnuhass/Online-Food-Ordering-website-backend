package com.aa_code.online_food_ordering_backend.repository;

import com.aa_code.online_food_ordering_backend.Model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantRepo extends JpaRepository<Restaurant, Long> {

    @Query("SELECT r FROM Restaurant r WHERE lower(r.name) LIKE lower(contact('%', :query, '%'))" +
            " OR lower(r,cuisineType) LIKE lower(contact('%', :query, '%'))")
    List<Restaurant> findBySearchQuery(String query);
    Restaurant findByOwnerId(Long userId);


}
