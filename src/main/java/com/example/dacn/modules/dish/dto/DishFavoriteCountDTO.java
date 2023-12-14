package com.example.dacn.modules.dish.dto;

import com.example.dacn.entity.Dish;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class DishFavoriteCountDTO
{
    private Dish dish;
    private Long favoriteCount;
}
