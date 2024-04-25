package com.aa_code.online_food_ordering_backend.request;

import com.aa_code.online_food_ordering_backend.Model.ContactInformation;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateRestaurantRequest {

    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private ContactInformation contactInformation;
    private String openingHours;
    private List<String> images;
}
