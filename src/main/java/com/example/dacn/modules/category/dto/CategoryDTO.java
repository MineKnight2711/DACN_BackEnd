package com.example.dacn.modules.category.dto;

import com.example.dacn.entity.Category;
import com.example.dacn.entity.Dish;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CategoryDTO {
    private String categoryID;
    private String categoryName;
    private String imageUrl;

    public Category convertToEntity() {
        Category category = new Category();
        category.setCategoryID(this.categoryID);
        category.setCategoryName(this.categoryName);
        category.setImageUrl(this.imageUrl);
        return category;
    }
}
