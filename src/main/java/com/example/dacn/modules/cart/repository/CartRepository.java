package com.example.dacn.modules.cart.repository;

import com.example.dacn.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,String>
{
    @Query(value = "SELECT @generated_id", nativeQuery = true)
    String findLatestAddressId();
    @Query("SELECT c FROM Cart c WHERE c.account.accountID = :accountId AND c.dish.dishID = :dishId")
    Cart findCartByAccountAndDishId(String accountId,String dishId);
    @Query("SELECT c FROM Cart c WHERE c.account.accountID = :accountId")
    List<Cart> findCartByAccountId(String accountId);
}
