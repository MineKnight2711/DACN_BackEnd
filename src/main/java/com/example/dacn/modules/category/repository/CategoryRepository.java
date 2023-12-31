package com.example.dacn.modules.category.repository;

import com.example.dacn.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    @Query(value = "SELECT @generated_id", nativeQuery = true)
    String findLatestAddressId();
}
