package com.example.dacn.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
@Getter
@Setter
@Entity(name="account_voucher")
@Table(name="account_voucher")
public class AccountVoucher {
    @EmbeddedId
    private AccountVoucherId accountVoucherId;
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false,insertable=false, updatable=false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "voucher_id",nullable = false,insertable=false, updatable=false)
    private Voucher voucher;
    @Data
    @Getter
    @Setter
    @Embeddable
    public static class AccountVoucherId implements Serializable {
        @Column(name = "account_id")
        private String account_id;
        @Column(name = "voucher_id")
        private String voucher_id;
    }
}


