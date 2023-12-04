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

    public Payment toEntity()
    {
        Payment newPayment=new Payment();
        newPayment.setPaymentMethod(this.paymentMethod);

        return newPayment;
    }
}
