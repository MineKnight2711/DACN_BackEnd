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
    private Long categoryID;
    private String categoryName;

    public Category convertToEntity() {
        Category category = new Category();
        category.setCategoryName(this.categoryName);
        return category;
    }
}
