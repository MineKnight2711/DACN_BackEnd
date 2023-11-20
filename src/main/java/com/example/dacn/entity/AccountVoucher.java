package com.example.dacn.entity;

import com.example.dacn.entity.ids.AccountVoucherId;
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

}


