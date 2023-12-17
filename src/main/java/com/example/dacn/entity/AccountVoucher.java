package com.example.dacn.entity;

import com.example.dacn.entity.ids.AccountVoucherId;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;

@Data
@Getter
@Setter
@Entity(name="AccountVoucher")
@Table(name="AccountVoucher")
public class AccountVoucher {
    @EmbeddedId
    private AccountVoucherId accountVoucherId;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false,insertable=false, updatable=false)
    private Account account;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "voucher_id",nullable = false,insertable=false, updatable=false)
    private Voucher voucher;
}


