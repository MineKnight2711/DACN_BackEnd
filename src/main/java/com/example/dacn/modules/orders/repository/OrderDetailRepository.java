package com.example.dacn.modules.orders.repository;

import com.example.dacn.entity.OrderDetail;
import com.example.dacn.entity.ids.OrderDetailsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository <OrderDetail, OrderDetailsId>
{
    @Query("SELECT details FROM OrderDetail details WHERE details.order.orderID = :orderID")
    List<OrderDetail> getOrderDetailByOrderId(String orderID);
}

