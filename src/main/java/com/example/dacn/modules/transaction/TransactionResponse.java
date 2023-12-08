package com.example.dacn.modules.transaction;

import com.example.dacn.entity.OrderDetail;
import com.example.dacn.entity.Orders;
import com.example.dacn.entity.PaymentDetails;
import com.example.dacn.modules.payos.model.PaymentResponse;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class TransactionResponse
{
    private String orderId;
    private Long paymentDetailsId;
    private PaymentResponse paymentResponse;
}
