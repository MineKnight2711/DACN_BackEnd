package com.example.dacn.modules.voucher.dto;


import com.example.dacn.entity.Voucher;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Getter
@Setter
public class VoucherDTO {
    private String voucherID;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expDate;
    private String voucherName;
    private String type;
    private double discountAmount;
    private Integer discountPercent;
    private Integer pointsRequired;

    public Voucher toEntity() {
        Voucher voucher = new Voucher();
        voucher.setVoucherID(this.voucherID);
        voucher.setStartDate(this.startDate);
        voucher.setExpDate(this.expDate);
        voucher.setVoucherName(this.voucherName);
        voucher.setPointsRequired(this.pointsRequired);
        voucher.setDiscountAmount(this.discountAmount);
        voucher.setDiscountPercent(this.discountPercent);
        voucher.setType(this.type);
        return voucher;
    }
}
