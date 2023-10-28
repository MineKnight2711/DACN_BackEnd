package com.example.dacn.modules.category.controller;

import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.category.dto.CategoryDTO;
import com.example.dacn.modules.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping
    public ResponseModel getAllCategory()
    {
        return categoryService.getAllCategory();
    }
    @GetMapping("/{categoryID}")
    public ResponseModel getCategoryById(@PathVariable String categoryID)
    {
        return categoryService.findById(categoryID);
    }
    @PostMapping
    public ResponseModel createCategory(@RequestParam("file") MultipartFile file, @ModelAttribute CategoryDTO categoryDTO)
    {
        return categoryService.createCategory(file,categoryDTO);
    }
}
