package com.example.dacn.modules.favorite.dto;

import com.example.dacn.entity.Account;
import com.example.dacn.entity.Dish;
import com.example.dacn.entity.Favorite;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class FavoriteDTO {
    private String favoriteID;
    private String accountID;
    private String dishID;

    public Favorite convertToEntity()
    {
        Favorite favorite = new Favorite();
        favorite.setFavoriteID(this.favoriteID);
        return favorite;
    }

}
