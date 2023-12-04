package com.example.dacn.modules.favorite.controller;

import com.example.dacn.entity.Favorite;
import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.account.service.AccountService;
import com.example.dacn.modules.dish.service.DishService;
import com.example.dacn.modules.favorite.dto.FavoriteDTO;
import com.example.dacn.modules.favorite.repository.FavoriteRepository;
import com.example.dacn.modules.favorite.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/favorite")
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;
    @GetMapping("/get-by-accountId/{accountId}")
    public ResponseModel getFavoritesByAccountID(@PathVariable("accountId") String accountID)
    {
        return favoriteService.getFavoritesByAccountID(accountID);
    }
    @PostMapping
    public ResponseModel addToFavorites(@ModelAttribute FavoriteDTO favoriteDTO)
    {

        return favoriteService.addToFavorites(favoriteDTO);
    }

    @DeleteMapping("/{favoriteID}")
    public ResponseModel removeFromFavorites(@PathVariable("favoriteID") String favoriteID) {
        FavoriteDTO favoriteDTO = new FavoriteDTO();
        favoriteDTO.setFavoriteID(favoriteID);
        return favoriteService.removeFromFavorites(favoriteDTO);
    }
}