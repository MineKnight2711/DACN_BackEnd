package com.example.dacn.modules.category.repository;

import com.example.dacn.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
//    @Query("SELECT c FROM Category c WHERE c.categoryID = ?1")
//    Category getById(Long id);
}
