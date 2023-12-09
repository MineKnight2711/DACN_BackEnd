package com.example.dacn.modules.orders.repository;

import com.example.dacn.entity.OrderDetail;
import com.example.dacn.entity.Orders;
import com.example.dacn.modules.orders.dto.OrderDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, String>
{
    @Query(value = "SELECT @generated_id", nativeQuery = true)
    String findLatestOrderId();
    @Query("SELECT o FROM Orders o WHERE o.account.accountID=:accountId")
    List<Orders> findOrdersByAccountId(String accountId);
    @Query("SELECT details FROM OrderDetail details WHERE details.order.orderID=:orderID")
    List<OrderDetail> findOrdersDetailByOrderID(String orderID);
    @Query("SELECT o FROM Orders o WHERE o.account.accountID=:accountId AND o.status=:status")
    List<Orders> findOrdersByOrderState(String accountId,String status);
}
