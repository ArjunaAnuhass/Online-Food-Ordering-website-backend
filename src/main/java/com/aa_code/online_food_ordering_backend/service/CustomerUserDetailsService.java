package com.aa_code.online_food_ordering_backend.service;

import com.aa_code.online_food_ordering_backend.Model.Enums.USER_ROLE;
import com.aa_code.online_food_ordering_backend.Model.User;
import com.aa_code.online_food_ordering_backend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByEmail(username);

        if (user==null){
            throw new UsernameNotFoundException("User Not found with email " + username);
        }

        USER_ROLE role = user.getRole();

        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(role.toString()));

        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
    }
}
