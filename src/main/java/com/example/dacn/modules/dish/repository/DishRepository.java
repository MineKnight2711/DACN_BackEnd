package com.example.dacn.modules.dish.repository;

import com.example.dacn.entity.Dish;
import com.example.dacn.modules.dish.dto.DishFavoriteCountDTO;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, String> {
    @Query(value = "SELECT @generated_id", nativeQuery = true)
    String findLatestAddressId();
    @Query("SELECT d AS dish, COUNT(f) AS favoriteCount FROM Dish d LEFT JOIN Favorite f ON d.dishID = f.dish.dishID WHERE d.category.categoryID = :categoryID GROUP BY d")
    List<Tuple> getAllDishesWithFavoriteAndByCategory( String categoryID);
    //Để đảm bảo rằng tất cả các bản ghi từ bảng Dish được bao gồm trong kết quả,
    // ngay cả khi không có Dish trong bảng Favorite,
    // ta sử dụng LEFT JOIN giữa bảng Dish và bảng Favorite
    @Query("SELECT d AS dish, COUNT(f) AS favoriteCount FROM Dish d LEFT JOIN Favorite f ON d.dishID = f.dish.dishID GROUP BY d")
    List<Tuple> getAllDishesWithFavoriteAndCount();
    @Query("SELECT d AS dish, COUNT(f) AS favoriteCount FROM Dish d LEFT JOIN Favorite f ON d.dishID = f.dish.dishID WHERE d.dishName LIKE %:dishName% GROUP BY d")
    List<Tuple> searchDishByDishName(String dishName, Pageable pageable);
}
