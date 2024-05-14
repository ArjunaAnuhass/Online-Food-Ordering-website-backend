package com.aa_code.online_food_ordering_backend.controller;

import com.aa_code.online_food_ordering_backend.Model.IngredientCategory;
import com.aa_code.online_food_ordering_backend.Model.IngredientsItem;
import com.aa_code.online_food_ordering_backend.request.IngredientCategoryRequest;
import com.aa_code.online_food_ordering_backend.request.IngredientItemRequest;
import com.aa_code.online_food_ordering_backend.service.IngredientsService;
import com.aa_code.online_food_ordering_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/admin/ingredients")
public class IngredientController {

    private final IngredientsService ingredientsService;
    private final UserService userService;

    @Autowired
    public IngredientController(IngredientsService ingredientsService, UserService userService) {
        this.ingredientsService = ingredientsService;
        this.userService = userService;
    }

    @PostMapping(path = "/ingredientCategory")
    public ResponseEntity<IngredientCategory> createNewIngredientCategory(@RequestBody IngredientCategoryRequest ingredientCategoryRequest) throws Exception {
        IngredientCategory ingredientCategory = ingredientsService.createIngredientCategory(ingredientCategoryRequest.getName(), ingredientCategoryRequest.getRestaurantId());

        return new ResponseEntity<>(ingredientCategory, HttpStatus.CREATED);
    }

    @PostMapping(path = "/ingredientItems")
    public ResponseEntity<IngredientsItem> createIngredientItems(@RequestBody IngredientItemRequest request) throws Exception {
        IngredientsItem ingredientsItem = ingredientsService.createIngredientItem(request.getRestaurantId(),request.getName(), request.getIngredientCategoryId());

        return new ResponseEntity<>(ingredientsItem, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}/stock")
    public ResponseEntity<IngredientsItem> updateStock(@PathVariable Long id) throws Exception {
        IngredientsItem ingredientsItem = ingredientsService.updateStock(id);

        return new ResponseEntity<>(ingredientsItem, HttpStatus.OK);
    }

    @GetMapping(path = "/restaurant/{id}")
    public ResponseEntity<List<IngredientsItem>> getRestaurantIngredients(@PathVariable Long id) throws Exception {
        List<IngredientsItem> ingredientsItems = ingredientsService.findRestaurantsIngredients(id);

        return new ResponseEntity<>(ingredientsItems, HttpStatus.OK);
    }

    @GetMapping(path = "/restaurant/{id}/ingredientCategory")
    public ResponseEntity<List<IngredientCategory>> getRestaurantIngredientCategory(@PathVariable Long id) throws Exception {
        List<IngredientCategory> ingredientCategories = ingredientsService.findIngredientCategoryByRestaurantId(id);

        return new ResponseEntity<>(ingredientCategories, HttpStatus.OK);
    }
}
