package com.example.dacn.modules.orders.repository;

import com.example.dacn.entity.OrderDetail;
import com.example.dacn.entity.ids.OrderDetailsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository <OrderDetail, OrderDetailsId>
{

}

