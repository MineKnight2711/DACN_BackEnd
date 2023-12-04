package com.example.dacn.modules.payment.controller;

import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class PaymentController
{
    @Autowired
    private PaymentService paymentService;
    @GetMapping
    public ResponseModel getAllPayment()
    {
        return paymentService.getAllPayment();
    }
}
