package com.example.dacn.modules.category.dto;

import com.example.dacn.entity.Category;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Data
@Getter
@Setter
public class CategoryDTO
{
    private String categoryID;
    private MultipartFile image;
    private String categoryName;

    public Category convertToEntity() {
        Category category = new Category();
        category.setCategoryID(this.categoryID);
        category.setCategoryName(this.categoryName);
        return category;
    }
}
