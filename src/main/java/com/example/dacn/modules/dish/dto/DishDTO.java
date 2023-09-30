package com.example.dacn.modules.dish.dto;

import com.example.dacn.entity.Dish;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class DishDTO {
    private Long dishID;
    private String dishName;
    private String description;
    private double price;
    private int inStock;
    private String imageUrl;
    private Long categoryID;

    public Dish convertToEntity() {
        Dish dish = new Dish();
        dish.setDishName(this.dishName);
        dish.setDescription(this.description);
        dish.setPrice(this.price);
        dish.setInStock(this.inStock);
        dish.setImageUrl(this.imageUrl);
        return dish;
    }
    public void updateDishToEntity(Dish dish) {
        dish.setDishName(this.dishName);
        dish.setDescription(this.description);
        dish.setPrice(this.price);
        dish.setInStock(this.inStock);
        dish.setImageUrl(this.imageUrl);

    }
}
