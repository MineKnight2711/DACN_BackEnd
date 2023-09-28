package com.example.dacn.modules.voucher.dto;

import com.example.dacn.entity.Account;
import com.example.dacn.entity.Review;
import com.example.dacn.entity.Voucher;
import com.example.dacn.utils.DatetimeDeserialize;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Getter
@Setter
public class VoucherDTO {
    private Long voucherID;

    @JsonDeserialize(using = DatetimeDeserialize.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @JsonDeserialize(using = DatetimeDeserialize.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expDate;
    private String voucherName;
    private double discount;

    public Voucher toEntity() {
        Voucher voucher = new Voucher();

        voucher.setStartDate(this.startDate);
        voucher.setExpDate(this.expDate);
        voucher.setVoucherName(this.voucherName);
        voucher.setDiscount(this.discount);
        return voucher;
    }
}
