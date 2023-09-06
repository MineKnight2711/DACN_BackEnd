package com.example.dacn.modules.category.service;

import com.example.dacn.entity.Category;
import com.example.dacn.modules.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    public Category findById(Long categoryID)
    {
       Optional<Category>  category=categoryRepository.findById(categoryID);
        if (category.isPresent()) {
            return category.get();
        } else {
            return null;
        }
    }
}
