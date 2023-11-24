package com.example.dacn.modules.transaction;

import com.example.dacn.modules.orders.dto.OrdersDTO;
import com.example.dacn.modules.payment.dto.PaymentDTO;
import com.example.dacn.modules.payment.dto.PaymentDetailsDTO;
import com.example.dacn.utils.DatetimeDeserialize;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
public class TransactionDTO
{
    private String accountId;
    private OrdersDTO ordersDTO;
    private PaymentDetailsDTO paymentDetailsDTO;
}
