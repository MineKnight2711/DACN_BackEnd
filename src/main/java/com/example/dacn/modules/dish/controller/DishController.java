package com.example.dacn.modules.dish.controller;

import com.example.dacn.entity.Dish;
import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.dish.dto.DishDTO;
import com.example.dacn.modules.dish.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    @PostMapping
    public ResponseModel addDish(@ModelAttribute DishDTO dishDTO)
    {
        return dishService.addDish(dishDTO);
    }
    @PutMapping("/{dishId}")
    public ResponseModel updateDish(@PathVariable("dishId") Long dishId, @ModelAttribute DishDTO dishDTO) {
        return dishService.updateDish(dishId,dishDTO);
    }
    @DeleteMapping("/{dishId}")
    public ResponseModel deleteDish(@PathVariable("dishId") Long dishId) {
        ResponseModel response = dishService.deleteDish(dishId);
        return response;
    }
}
