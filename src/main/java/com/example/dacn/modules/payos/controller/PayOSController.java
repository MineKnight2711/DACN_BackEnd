package com.example.dacn.modules.payos.controller;

import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.payos.model.PaymentRequestBody;
import com.example.dacn.modules.payos.model.PaymentResponse;
import com.example.dacn.modules.payos.service.PayOSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payos")
public class PayOSController
{
    @Autowired
    private PayOSService payOSService;
    @PostMapping
    public PaymentResponse getPaymentLink(@ModelAttribute PaymentRequestBody body) throws Exception {
        return payOSService.getPaymentLink(body);
    }
}
