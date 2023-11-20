package com.example.dacn.modules.orders.repository;

import com.example.dacn.entity.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, String>
{
    @Query(value = "SELECT @generated_id", nativeQuery = true)
    String findLatestOrderId();
}
