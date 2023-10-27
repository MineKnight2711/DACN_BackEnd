package com.example.dacn.modules.category.service;

import com.example.dacn.entity.Category;
import com.example.dacn.entity.Dish;
import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.category.dto.CategoryDTO;
import com.example.dacn.modules.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public ResponseModel getAllCategory(){
        List<Category> listCategory=categoryRepository.findAll();
        if(!listCategory.isEmpty()){
            return new ResponseModel("Success",listCategory);
        }
        return new ResponseModel("Empty",null);
    }
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
        categoryDTO.setCategoryID("");
        try
        {
            Category newCategory = categoryDTO.convertToEntity();
            categoryRepository.save(newCategory);
            return new ResponseModel("Success",newCategory);
        }
        catch (Exception e)
        {
            return new ResponseModel("Fail",null);
        }
    }
}
