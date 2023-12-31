package com.example.dacn.modules.dish.controller;

import com.example.dacn.entity.Dish;
import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.dish.dto.DishDTO;
import com.example.dacn.modules.dish.service.DishService;
import com.example.dacn.modules.favorite.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@RestController
@RequestMapping("/api/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private FavoriteService favoriteService;
    @PostMapping
    public ResponseModel addDish(@ModelAttribute DishDTO dishDTO)
    {
        return dishService.addDish(dishDTO);
    }
    @PutMapping("/{dishId}")
    public ResponseModel updateDish(@PathVariable("dishId") String dishId, @ModelAttribute DishDTO dishDTO)
    {
        return dishService.updateDish(dishId,dishDTO);
    }
    @DeleteMapping("/{dishId}")
    public ResponseModel deleteDish(@PathVariable("dishId") String dishId)
    {
        ResponseModel response = dishService.deleteDish(dishId);
        return response;
    }
    @GetMapping
    public ResponseModel getAllDishes()
    {
        return dishService.getAllDishes();
    }
    @GetMapping("/with-favorite")
    public ResponseModel getAllDishesWithFavorite()
    {
        return dishService.getAllDishesWithFavorite();
    }
    @GetMapping("/get-by-categoryId/{categoryID}")
    public ResponseModel getByCategoryID(@PathVariable("categoryID") String categoryID)
    {
        return dishService.getByCategoryId(categoryID);
    }
    @GetMapping("/search-dish-by-name/{dishName}")
    public ResponseModel searchDishByName(@PathVariable("dishName") String dishName)
    {
        return dishService.searchDishByName(dishName);
    }
}
