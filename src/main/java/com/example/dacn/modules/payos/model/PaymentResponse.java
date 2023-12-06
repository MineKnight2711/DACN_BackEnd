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
//    private String signature;
    @Data
    @Getter
    @Setter
    public static class PaymentData {
//        private String bin;
//        private String accountNumber;
//        private String accountName;
        private int amount;
        private String description;
        private int orderCode;
//        private String paymentLinkId;
        private String status;
        private String checkoutUrl;
        private String qrCode;
    }
}
