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

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService
{
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ImageService imageService;

    public ResponseModel getAllCategory()
    {
        List<Category> listCategory=categoryRepository.findAll();
        if(!listCategory.isEmpty())
        {
            return new ResponseModel("Success",listCategory);
        }
        return new ResponseModel("CategoryEmpty","Lỗi danh mục trống");
    }
    public ResponseModel findById(String categoryID)
    {
        Category category=categoryRepository.findById(categoryID).orElse(null);
        if (category!=null)
        {
            return new ResponseModel("Found",category);
        }
        else
        {
            return new ResponseModel("NotFound","Không tìm thấy danh mục");
        }
    }

    public ResponseModel createCategory( CategoryDTO categoryDTO)
    {
        categoryDTO.setCategoryID("");

        try
        {

            Category newCategory = categoryDTO.convertToEntity();

            categoryRepository.save(newCategory);
            String categoryId=categoryRepository.findLatestAddressId();
            String imageUrl=imageService.uploadImage(categoryDTO.getImage(), "categoryImage/",categoryId);
            newCategory.setCategoryID(categoryId);
            newCategory.setImageUrl(imageUrl);
            Category savedCate=categoryRepository.save(newCategory);
            return new ResponseModel("Success",savedCate);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseModel("Fail","Không thể thêm mới danh mục");
        }
    }

    public ResponseModel updateCategory(String categoryID, CategoryDTO categoryDTO)
    {
        try
        {
            Category categoryOptional = categoryRepository.findById(categoryID).orElse(null);

            if (categoryOptional!=null)
            {
                categoryDTO.setCategoryID(categoryID);
                Category updatedCate=categoryDTO.convertToEntity();
                if(categoryDTO.getImage()!=null)
                {
                    String imageUrl=imageService.uploadImage(categoryDTO.getImage(),"categoryImage/",categoryID);
                    updatedCate.setImageUrl(imageUrl);
                }
                else
                {
                    updatedCate.setImageUrl(categoryOptional.getImageUrl());
                }
                categoryRepository.save(updatedCate);

                return new ResponseModel("Success", updatedCate);
            }
            return new ResponseModel("CategoryNotFound", "Không tìm thấy danh mục chỉnh sửa");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            return new ResponseModel("Fail", "Lỗi lưu ảnh");
        }
    }
    public ResponseModel deleteCategory(String categoryID)
    {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryID);

        if (!categoryOptional.isEmpty())
        {
            Category existingCategory = categoryOptional.get();
            if(imageService.deleteExistImage("categoryImage/",existingCategory.getCategoryName()))
            {
                System.out.println("Đã xoá hình"+ existingCategory.getCategoryName());
                categoryRepository.delete(existingCategory);
                return new ResponseModel("Success", existingCategory);
            }

            return new ResponseModel("Fail", "Lỗi khi xoá ảnh của danh mục");
        }
        return new ResponseModel("CategoryNotFound", "Không tìm thấy danh mục");

    }
}
