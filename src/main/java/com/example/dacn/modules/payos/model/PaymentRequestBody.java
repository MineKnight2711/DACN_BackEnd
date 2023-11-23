package com.example.dacn.modules.payos.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PaymentRequestBody
{

    private double amount;
    private String cancelUrl;
    private String description;
    private Long orderCode;
    private String returnUrl;
    private String signature;
}
