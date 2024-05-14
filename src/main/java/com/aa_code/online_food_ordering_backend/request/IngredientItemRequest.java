package com.aa_code.online_food_ordering_backend.request;

import lombok.Data;

@Data
public class IngredientItemRequest {

    private String name;
    private Long ingredientCategoryId;
    private Long restaurantId;
}
