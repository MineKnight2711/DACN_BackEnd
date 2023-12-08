package com.example.dacn.modules.transaction.dto;

import com.example.dacn.modules.orders.dto.OrdersDTO;
import com.example.dacn.modules.payment.dto.PaymentDetailsDTO;
import com.example.dacn.modules.payos.model.PaymentRequestBody;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Getter
@Setter
public class CODTransactionDTO
{
    private String accountId;
    private OrdersDTO ordersDTO;
    private PaymentDetailsDTO paymentDetailsDTO;
}
