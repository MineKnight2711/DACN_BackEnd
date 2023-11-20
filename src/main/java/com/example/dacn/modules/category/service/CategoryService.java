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
       Optional<Category> category=categoryRepository.findById(categoryID);
        if (category.isPresent())
        {
            return new ResponseModel("Found",category.get());
        }
        else
        {
            return new ResponseModel("NotFound","Không tìm thấy danh mục");
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
            return new ResponseModel("Fail","Không thể thêm mới danh mục");
        }
    }

    public ResponseModel updateCategory(String categoryID, CategoryDTO categoryDTO)
    {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryID);

        if (!categoryOptional.isEmpty())
        {
            Category existingCategory = categoryOptional.get();
            existingCategory.setCategoryName(categoryDTO.getCategoryName());

            categoryRepository.save(existingCategory);

            return new ResponseModel("Success", existingCategory);
        }
        return new ResponseModel("CategoryNotFound", "Không tìm thấy danh mục chỉnh sửa");

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
