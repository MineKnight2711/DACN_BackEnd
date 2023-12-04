package com.example.dacn.modules.favorite.repository;

import com.example.dacn.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, String> {
    @Query("SELECT f FROM Favorite f WHERE f.account.accountID=:accountID AND f.dish.dishID = :dishID ")
    Favorite getDuplicateFavorite(String accountID,String dishID);
    @Query("SELECT f FROM Favorite f WHERE f.account.accountID = :accountID ")
    List<Favorite> getFavoriteById(String accountID);
}
