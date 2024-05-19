package com.aa_code.online_food_ordering_backend.controller;

import com.aa_code.online_food_ordering_backend.Model.Category;
import com.aa_code.online_food_ordering_backend.Model.Restaurant;
import com.aa_code.online_food_ordering_backend.Model.User;
import com.aa_code.online_food_ordering_backend.service.CategoryService;
import com.aa_code.online_food_ordering_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class CategoryController {

    private final CategoryService categoryService;
    private final UserService userService;

    @Autowired
    public CategoryController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @PostMapping(path = "/admin/category")
    public ResponseEntity<Category> createCategory(@RequestBody Category category, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Category category1 = categoryService.createCategory(category.getName(), user.getId());

        return new ResponseEntity<>(category1, HttpStatus.CREATED);
    }

    @GetMapping(path = "/category/restaurant")
    public ResponseEntity<List<Category>> getRestaurantCategory(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        List<Category> category1 = categoryService.findCategoryByRestaurantId(user.getId());

        return new ResponseEntity<>(category1, HttpStatus.OK);
    }
}


