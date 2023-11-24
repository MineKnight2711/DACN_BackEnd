package com.example.dacn.modules.payment.dto;

import com.example.dacn.entity.Payment;
import com.example.dacn.entity.PaymentDetails;
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
public class PaymentDetailsDTO
{
    private String paymentId;
    private String info;
    private String status;
    private double amount;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date paidTime;
    public PaymentDetails toEntity()
    {
        PaymentDetails newPaymentDetails=new PaymentDetails();
        newPaymentDetails.setStatus(this.status);
        newPaymentDetails.setInfo(this.info);
        newPaymentDetails.setAmount(this.amount);
        newPaymentDetails.setPaidTime(this.paidTime);
        return newPaymentDetails;
    }
}
