package com.example.dacn.modules.favorite.dto;

import com.example.dacn.entity.Dish;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class FavoriteWithCountAccountDTO
{
    private Dish dish;
    private int uniqueAccountCount;
}
