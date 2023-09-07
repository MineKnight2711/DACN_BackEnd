package com.example.dacn.modules.category.service;

import com.example.dacn.entity.Category;
import com.example.dacn.entity.Dish;
import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.category.dto.CategoryDTO;
import com.example.dacn.modules.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    public ResponseModel findById(Long categoryID)
    {
       Optional<Category>  category=categoryRepository.findById(categoryID);
        if (category.isPresent())
        {
            return new ResponseModel("Found",category.get());
        }
        else
        {
            return new ResponseModel("NotFound",null);
        }
    }

    public ResponseModel createCategory(CategoryDTO categoryDTO)
    {
        try
        {
            Category  newCategory = categoryDTO.convertToEntity();
            categoryRepository.save(newCategory);
            return new ResponseModel("Success",newCategory);
        }
        catch (Exception e)
        {
            return new ResponseModel("Fail",null);
        }
    }
}
