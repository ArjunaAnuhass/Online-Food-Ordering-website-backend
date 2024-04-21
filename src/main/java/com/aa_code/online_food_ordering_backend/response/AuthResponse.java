package com.aa_code.online_food_ordering_backend.response;

import com.aa_code.online_food_ordering_backend.Model.Enums.USER_ROLE;
import lombok.Data;

@Data
public class AuthResponse {

    private String jwt;
    private String message;
    private USER_ROLE role;
}
