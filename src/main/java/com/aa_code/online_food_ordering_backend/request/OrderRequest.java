package com.aa_code.online_food_ordering_backend.request;

import com.aa_code.online_food_ordering_backend.Model.Address;
import lombok.Data;

@Data
public class OrderRequest {

    private Long restaurantId;
    private Address deliveryAddress;
}
