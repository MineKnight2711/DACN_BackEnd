package com.example.dacn.modules.favorite.service;

import com.example.dacn.entity.Account;
import com.example.dacn.entity.Dish;
import com.example.dacn.entity.Favorite;
import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.account.repository.AccountRepository;
import com.example.dacn.modules.account.service.AccountService;
import com.example.dacn.modules.dish.repository.DishRepository;
import com.example.dacn.modules.dish.service.DishService;
import com.example.dacn.modules.favorite.dto.FavoriteDTO;
import com.example.dacn.modules.favorite.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private DishService dishService;

    public ResponseModel addToFavorites(FavoriteDTO favoriteDTO) {
            favoriteDTO.setFavoriteID("");
            try
            {
                Favorite isDuplicate=favoriteRepository.getDuplicateFavorite(favoriteDTO.getAccountID(),favoriteDTO.getDishID());
                if(isDuplicate!=null)
                {
                    return new ResponseModel("AlreadyFavorite", "Bạn đã thích món này rồi!");
                }
                Favorite favorite = favoriteDTO.convertToEntity();

                Account account =accountService.findById(favoriteDTO.getAccountID()) ;

                Dish dish = dishService.findById(favoriteDTO.getDishID());

                favorite.setAccount(account);
                favorite.setDish(dish);
                favoriteRepository.save(favorite);

                return new ResponseModel("Success", favorite);
            }
            catch (NoSuchElementException e)
            {
                return new ResponseModel(e.getMessage(), null);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return new ResponseModel("SomethingWrong", null);
            }
}

    public ResponseModel removeFromFavorites(String dishID,String accountID)
    {
        try
        {
            Favorite favorite = favoriteRepository.getDuplicateFavorite(accountID,dishID);
            if(favorite!=null)
            {
                favoriteRepository.delete(favorite);
                return new ResponseModel("Success", null);
            }
            return new ResponseModel("Fail", null);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseModel("SomethingWrong", "Có lỗi xảy ra");
        }
    }
    public ResponseModel getFavoritesByAccountID(String accountID)
    {
        return new ResponseModel("Success",favoriteRepository.getFavoriteById(accountID));
    }

    public ResponseModel getAccountFavoriteDish(String dishID, String accountID)
    {
        Favorite favorite = favoriteRepository.getDuplicateFavorite(accountID,dishID);
        if(favorite!=null)
        {
            return new ResponseModel("Success", favorite);
        }
        return new ResponseModel("NotFound", null);
    }
}


