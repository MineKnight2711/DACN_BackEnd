package com.example.dacn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;


@Data
@Getter
@Setter
@Entity(name = "Voucher")
@Table(name = "Voucher")
public class Voucher {
    @Id
    private String voucherID;
    @Column(name = "startDate")
    @JsonProperty("startDate")

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @Column(name = "expDate")
    @JsonProperty("expDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expDate;
    @Column(name = "voucherName")
    private String voucherName;
    @Column(name = "discount")
    private double discount;
    @ManyToMany
    @JoinTable(name = "AccountVoucher",
            joinColumns = @JoinColumn(name = "voucher_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id"))
    @JsonIgnore
//    @JsonManagedReference
    private List<Account> accounts;

    //Quan hệ 1 nhiều tới bảng order
    @OneToMany(mappedBy = "voucher", cascade = CascadeType.ALL)
    @JsonIgnore
    @JsonManagedReference
    private List<Orders> orders;
}
