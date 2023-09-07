package com.example.dacn.modules.category.controller;

import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.category.dto.CategoryDTO;
import com.example.dacn.modules.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/{categoryID}")
    public ResponseModel getCategoryById(@PathVariable Long categoryID)
    {
        return categoryService.findById(categoryID);
    }
    @PostMapping
    public ResponseModel createCategory(@ModelAttribute CategoryDTO categoryDTO)
    {
        return categoryService.createCategory(categoryDTO);
    }
}
