package com.example.dacn.modules.payment.repository;

import com.example.dacn.entity.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Long>
{
    @Query("SELECT p FROM PaymentDetails p WHERE p.orders.orderID = :orderId ")
    PaymentDetails getPaymentDetailsByOrderId(String orderId);
}
