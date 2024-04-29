package com.aa_code.online_food_ordering_backend.service;

import com.aa_code.online_food_ordering_backend.Dto.RestaurantDto;
import com.aa_code.online_food_ordering_backend.Model.Restaurant;
import com.aa_code.online_food_ordering_backend.Model.User;
import com.aa_code.online_food_ordering_backend.request.CreateRestaurantRequest;

import java.util.List;

public interface RestaurantService {

    public Restaurant createRestaurant(CreateRestaurantRequest request, User user);

    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception;

    public void deleteRestaurant(Long restaurantId) throws Exception;

    public List<Restaurant> getAllRestaurant();

    public List<Restaurant> searchRestaurant(String keyword);

    public Restaurant findRestaurantById(Long id) throws Exception;

    public Restaurant getRestaurantByUserId(Long userId) throws Exception;

    public RestaurantDto addToFavorite(Long restaurantId, User user) throws Exception;

    public Restaurant updateRestaurantStatus(Long id) throws Exception;
}
