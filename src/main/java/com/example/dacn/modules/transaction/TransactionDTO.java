package com.example.dacn.modules.transaction;

import com.example.dacn.modules.orders.dto.OrdersDTO;
import com.example.dacn.modules.payment.dto.PaymentDTO;
import com.example.dacn.modules.payment.dto.PaymentDetailsDTO;
import com.example.dacn.modules.payos.model.PaymentRequestBody;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Getter
@Setter
public class TransactionDTO
{
    private String accountId;
    private OrdersDTO ordersDTO;
    private PaymentDetailsDTO paymentDetailsDTO;
    private PaymentRequestBody paymentRequestBody;
}
