package com.example.dacn.modules.dish.repository;

import com.example.dacn.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, String> {
    @Query(value = "SELECT @generated_id", nativeQuery = true)
    String findLatestAddressId();
    @Query("SELECT d FROM Dish d WHERE d.category.categoryID = :categoryID")
    List<Dish> getByCategory(String categoryID);
}
