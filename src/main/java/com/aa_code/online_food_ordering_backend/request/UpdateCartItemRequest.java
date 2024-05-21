package com.aa_code.online_food_ordering_backend.request;

import lombok.Data;

@Data
public class UpdateCartItemRequest {

    private Long cartItemId;
    private int quantity;
}
