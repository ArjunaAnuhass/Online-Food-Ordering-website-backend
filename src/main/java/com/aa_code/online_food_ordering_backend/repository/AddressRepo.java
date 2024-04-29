package com.aa_code.online_food_ordering_backend.repository;

import com.aa_code.online_food_ordering_backend.Model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<Address, Long> {
}
