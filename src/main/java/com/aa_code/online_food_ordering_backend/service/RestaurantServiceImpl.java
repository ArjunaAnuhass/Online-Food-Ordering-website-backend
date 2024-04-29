package com.aa_code.online_food_ordering_backend.service;

import com.aa_code.online_food_ordering_backend.Dto.RestaurantDto;
import com.aa_code.online_food_ordering_backend.Model.Address;
import com.aa_code.online_food_ordering_backend.Model.Restaurant;
import com.aa_code.online_food_ordering_backend.Model.User;
import com.aa_code.online_food_ordering_backend.repository.AddressRepo;
import com.aa_code.online_food_ordering_backend.repository.RestaurantRepo;
import com.aa_code.online_food_ordering_backend.repository.UserRepo;
import com.aa_code.online_food_ordering_backend.request.CreateRestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService{

    private final RestaurantRepo restaurantRepo;
    private final AddressRepo addressRepo;
    private final UserService userService;
    private final UserRepo userRepo;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepo restaurantRepo, AddressRepo addressRepo, UserService userService, UserRepo userRepo) {
        this.restaurantRepo = restaurantRepo;
        this.addressRepo = addressRepo;
        this.userService = userService;
        this.userRepo = userRepo;
    }

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest request, User user) {

        Address address = addressRepo.save(request.getAddress());

        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setContactInformation(request.getContactInformation());
        restaurant.setCuisineType(request.getCuisineType());
        restaurant.setDescription(request.getDescription());
        restaurant.setImages(request.getImages());
        restaurant.setName(request.getName());
        restaurant.setOpeningHours(request.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);

        return restaurantRepo.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception {

        Restaurant restaurant = findRestaurantById(restaurantId);

        if (restaurant.getCuisineType()!= null){
            restaurant.setCuisineType(updatedRestaurant.getCuisineType());
        }
        if (restaurant.getDescription()!=null){
            restaurant.setDescription(updatedRestaurant.getDescription());
        }
        if (restaurant.getName()!=null){
            restaurant.setName(updatedRestaurant.getName());
        }
        if (restaurant.getContactInformation()==null){
            restaurant.setContactInformation(updatedRestaurant.getContactInformation());
        }
        else if (restaurant.getContactInformation()!=null){
            restaurant.setContactInformation(updatedRestaurant.getContactInformation());
        }

        return restaurantRepo.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {

        Restaurant restaurant = findRestaurantById(restaurantId);

        restaurantRepo.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepo.findAll() ;
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepo.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {

        Optional<Restaurant> optional = restaurantRepo.findById(id);

        if (optional.isEmpty()){
            throw new Exception("Restaurant not found with id: " + id);
        }

        return optional.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {

        Restaurant restaurant = restaurantRepo.findByOwnerId(userId);

        if (restaurant == null){
            throw new Exception("Restaurant Not found with owner id: " + userId);
        }

        return restaurant;
    }

    @Override
    public RestaurantDto addToFavorite(Long restaurantId, User user) throws Exception {

        Restaurant restaurant = findRestaurantById(restaurantId);

        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setDescription(restaurant.getDescription());
        restaurantDto.setImages(restaurant.getImages());
        restaurantDto.setTitle(restaurant.getName());
        restaurantDto.setId(restaurantId);

        if (user.getFavorites().contains(restaurantDto)){
            user.getFavorites().remove(restaurantDto);
        }
        else {
            user.getFavorites().add(restaurantDto);
        }

        userRepo.save(user);

        return restaurantDto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {

        Restaurant restaurant = findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());

        return restaurantRepo.save(restaurant);
    }
}
