package com.example.dacn.modules.payment.dto;

import com.example.dacn.entity.Payment;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Getter
@Setter
public class PaymentDTO
{
    private String paymentMethod;
//    private String info;
//    private double amount;
//    @Temporal(TemporalType.TIMESTAMP)
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    private Date paidTime;
    public Payment toEntity()
    {
        Payment newPayment=new Payment();
        newPayment.setPaymentMethod(this.paymentMethod);
//        newPayment.setInfo(this.info);
//        newPayment.setAmount(this.getAmount());
//        newPayment.setPaidTime(this.paidTime);
        return newPayment;
    }
}
