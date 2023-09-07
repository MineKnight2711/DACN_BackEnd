package com.example.dacn.modules.dish.service;

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
            return new ResponseModel("Fail",null);
        }
    }
}
