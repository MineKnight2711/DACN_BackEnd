package com.example.dacn.modules.payment.repository;

import com.example.dacn.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment , String> {

}
