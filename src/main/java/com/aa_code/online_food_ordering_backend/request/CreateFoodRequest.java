package com.aa_code.online_food_ordering_backend.request;

import com.aa_code.online_food_ordering_backend.Model.Category;
import com.aa_code.online_food_ordering_backend.Model.IngredientsItem;
import lombok.Data;

import java.util.List;

@Data
public class CreateFoodRequest {

    private String name;
    private String description;
    private Long price;

    private Category category;
    private List<String> images;

    private Long restaurantId;
    private boolean vegetarian;
    private boolean seasonal;
    private List<IngredientsItem> ingredients;
}
