package com.example.dacn.modules.payment.service;

import com.example.dacn.entity.Orders;
import com.example.dacn.entity.Payment;
import com.example.dacn.entity.PaymentDetails;
import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.payment.dto.PaymentDTO;
import com.example.dacn.modules.payment.dto.PaymentDetailsDTO;
import com.example.dacn.modules.payment.repository.PaymentDetailsRepository;
import com.example.dacn.modules.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService
{
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private PaymentDetailsRepository paymentDetailsRepository;

    public PaymentDetails savePayment(Orders order, PaymentDetailsDTO dto)
    {
        Payment payment=paymentRepository.findById(dto.getPaymentId()).orElse(null);
        if(payment!=null)
        {
            PaymentDetails newPaymentDetails = dto.toEntity();
            newPaymentDetails.setOrders(order);
            newPaymentDetails.setPayment(payment);
            PaymentDetails savedPamentDetails= paymentDetailsRepository.save(newPaymentDetails);
            return savedPamentDetails;
        }
        return null;
    }
    public ResponseModel getAllPayment()
    {
        List<Payment> listPayment=paymentRepository.findAll();
        return new ResponseModel("Success",listPayment);
    }
//    public ResponseModel savePayment(PaymentDTO dto)
//    {
//
//    }
}
