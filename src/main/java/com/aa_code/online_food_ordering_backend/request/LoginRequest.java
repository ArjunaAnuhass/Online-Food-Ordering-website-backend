package com.aa_code.online_food_ordering_backend.request;

import lombok.Data;

@Data
public class LoginRequest {

    private String email;
    private String password;
}
