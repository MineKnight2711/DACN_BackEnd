package com.example.dacn.modules.payos.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PaymentResponse
{
    private String code;
    private String desc;
    private PaymentData data;
    @Data
    @Getter
    @Setter
    public static class PaymentData {
        private double amount;
        private String description;
        private int orderCode;
        private String status;
        private String checkoutUrl;
        private String qrCode;
    }
}
