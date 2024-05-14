package com.aa_code.online_food_ordering_backend.service;

import com.aa_code.online_food_ordering_backend.Model.IngredientCategory;
import com.aa_code.online_food_ordering_backend.Model.IngredientsItem;
import com.aa_code.online_food_ordering_backend.Model.Restaurant;
import com.aa_code.online_food_ordering_backend.repository.IngredientCategoryRepo;
import com.aa_code.online_food_ordering_backend.repository.IngredientItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientsService{

    private final IngredientCategoryRepo ingredientCategoryRepo;
    private final IngredientItemRepo ingredientItemRepo;
    private final RestaurantService restaurantService;

    @Autowired
    public IngredientServiceImpl(IngredientCategoryRepo ingredientCategoryRepo, IngredientItemRepo ingredientItemRepo, RestaurantService restaurantService) {
        this.ingredientCategoryRepo = ingredientCategoryRepo;
        this.ingredientItemRepo = ingredientItemRepo;
        this.restaurantService = restaurantService;
    }

    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);

        IngredientCategory ingredientCategory = new IngredientCategory();
        ingredientCategory.setRestaurant(restaurant);
        ingredientCategory.setName(name);

        return ingredientCategoryRepo.save(ingredientCategory);
    }

    @Override
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception {
        Optional<IngredientCategory> optionalIngredientCategory = ingredientCategoryRepo.findById(id);

        if (optionalIngredientCategory.isEmpty()){
            throw new Exception("Ingredient category not found!");
        }
        return optionalIngredientCategory.get();
    }

    @Override
    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception {
        restaurantService.findRestaurantById(id);
        return ingredientCategoryRepo.findByRestaurantId(id);
    }

    @Override
    public IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long ingredientCategoryId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory ingredientCategory = findIngredientCategoryById(ingredientCategoryId);

        IngredientsItem ingredientsItem = new IngredientsItem();
        ingredientsItem.setName(ingredientName);
        ingredientsItem.setRestaurant(restaurant);
        ingredientsItem.setCategory(ingredientCategory);

        IngredientsItem ingredientsItem1 = ingredientItemRepo.save(ingredientsItem);
        ingredientCategory.getIngredients().add(ingredientsItem1);
        return ingredientsItem1;
    }

    @Override
    public List<IngredientsItem> findRestaurantsIngredients(Long restaurantId) throws Exception {
        return ingredientItemRepo.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {
        Optional<IngredientsItem> optionalIngredientsItem = ingredientItemRepo.findById(id);

        if (optionalIngredientsItem.isEmpty()){
            throw new Exception("Ingredient not found!");
        }
        IngredientsItem ingredientsItem = optionalIngredientsItem.get();
        ingredientsItem.setInStock(!ingredientsItem.isInStock());
        return ingredientItemRepo.save(ingredientsItem);
    }
}
