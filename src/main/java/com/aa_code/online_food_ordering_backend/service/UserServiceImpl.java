package com.aa_code.online_food_ordering_backend.service;

import com.aa_code.online_food_ordering_backend.Config.JwtProvider;
import com.aa_code.online_food_ordering_backend.Model.User;
import com.aa_code.online_food_ordering_backend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepo userRepo;
    private final JwtProvider jwtProvider;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, JwtProvider jwtProvider) {
        this.userRepo = userRepo;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        User user = findUserByEmail(email);

        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {

        User user = userRepo.findByEmail(email);

        if (user == null){
            throw new Exception("User Not Found!");
        }

        return user;
    }
}
