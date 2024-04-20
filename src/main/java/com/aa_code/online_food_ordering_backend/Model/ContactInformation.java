package com.aa_code.online_food_ordering_backend.Model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
public class ContactInformation {

    private String email;
    private String mobile;
    private String twitter;
    private String instagram;
}
