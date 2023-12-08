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
    public boolean cancelPayment(Long detailsId)
    {
        PaymentDetails details=paymentDetailsRepository.findById(detailsId).orElse(null);
        if(details!=null)
        {
            paymentDetailsRepository.delete(details);
            return true;
        }
        return false;
    }
    public boolean updatePaymentDetails(Long paymentDetailsId) {
        try{
            PaymentDetails details=paymentDetailsRepository.findById(paymentDetailsId).orElse(null);
            if(details!=null)
            {
                paymentDetailsRepository.save(details);
                return true;
            }
            return false;
        }catch (Exception ex){
            return false;
        }
    }

    public ResponseModel getAllPayment()
    {
        return new ResponseModel("Success",paymentRepository.findAll());
    }
}
