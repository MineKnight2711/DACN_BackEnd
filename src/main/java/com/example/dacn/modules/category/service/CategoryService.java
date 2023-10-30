package com.example.dacn.modules.category.service;

import com.example.dacn.entity.Category;
import com.example.dacn.entity.Dish;
import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.category.dto.CategoryDTO;
import com.example.dacn.modules.category.repository.CategoryRepository;
import com.example.dacn.utils.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ImageService imageService;

    public ResponseModel getAllCategory(){
        List<Category> listCategory=categoryRepository.findAll();
        if(!listCategory.isEmpty()){
            return new ResponseModel("Success",listCategory);
        }
        return new ResponseModel("Empty",null);
    }
    public ResponseModel findById(String categoryID)
    {
       Optional<Category> category=categoryRepository.findById(categoryID);
        if (category.isPresent())
        {
            return new ResponseModel("Found",category.get());
        }
        else
        {
            return new ResponseModel("NotFound",null);
        }
    }

    public ResponseModel createCategory(MultipartFile file, CategoryDTO categoryDTO)
    {
        categoryDTO.setCategoryID("");

        try
        {
            String imageUrl=imageService.uploadImage(file,"categoryImage/",categoryDTO.getCategoryName());
            Category newCategory = categoryDTO.convertToEntity();
            newCategory.setImageUrl(imageUrl);
            categoryRepository.save(newCategory);
            return new ResponseModel("Success",newCategory);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseModel("Fail",null);
        }
    }
}
