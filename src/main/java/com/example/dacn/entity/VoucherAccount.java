package com.example.dacn.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity(name = "VoucherAccount")
@Table(name = "Voucher_Account")
public class VoucherAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voucherAccountID;

    @ManyToOne
    @JoinColumn(name = "voucherID",nullable = false)
    private Voucher voucher;

    @ManyToOne
    @JoinColumn(name = "accountID",nullable = false)
    private Account account;
}
