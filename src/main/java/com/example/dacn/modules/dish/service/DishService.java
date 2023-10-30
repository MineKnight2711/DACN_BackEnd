package com.example.dacn.modules.dish.service;

import com.example.dacn.entity.Account;
import com.example.dacn.entity.Category;
import com.example.dacn.entity.Dish;
import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.category.repository.CategoryRepository;
import com.example.dacn.modules.category.service.CategoryService;
import com.example.dacn.modules.dish.dto.DishDTO;
import com.example.dacn.modules.dish.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DishService {
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private CategoryService categoryService;

    public ResponseModel addDish(DishDTO dishDTO)
    {
        dishDTO.setDishID("");
        try
        {
            ResponseModel categoryResponse=categoryService.findById(dishDTO.getCategoryID());
            if(categoryResponse.getData()==null)
            {
                return new ResponseModel("CategoryNotFound",null);
            }
            Dish newDish = dishDTO.convertToEntity();
            newDish.setCategory((Category) categoryResponse.getData());
            dishRepository.save(newDish);

            return new ResponseModel("Success",newDish);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseModel("Fail",null);
        }
    }
    public ResponseModel updateDish(String dishID,DishDTO dishDTO) {
        try {
            ResponseModel categoryResponse = categoryService.findById(dishDTO.getCategoryID());
            if (categoryResponse.getData() == null) {
                return new ResponseModel("CategoryNotFound", null);
            }

            Optional<Dish> optionalDish = dishRepository.findById(dishID);
            if (optionalDish.isPresent()) {
                Dish dish = optionalDish.get();
                dishDTO.updateDishToEntity(dish);
                dish.setCategory((Category) categoryResponse.getData());
                dishRepository.save(dish);
                return new ResponseModel("Success", dish);
            } else {
                return new ResponseModel("DishNotFound", null);
            }
        } catch (Exception e) {
            return new ResponseModel("Fail", null);
        }
    }

    public Dish findById(String dishID)
    {
        Optional<Dish> dish=dishRepository.findById(dishID);

        if(dish.isEmpty())
        {
            return null;
        }
        return dish.get();
    }
}
