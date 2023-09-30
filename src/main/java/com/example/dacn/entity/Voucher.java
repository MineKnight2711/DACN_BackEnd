package com.example.dacn.entity;

import com.example.dacn.utils.DatetimeDeserialize;
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
    @JsonDeserialize(using = DatetimeDeserialize.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @Column(name = "expDate")
    @JsonProperty("expDate")
    @JsonDeserialize(using = DatetimeDeserialize.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expDate;
    @Column(name = "voucherName")
    private String voucherName;
    @Column(name = "discount")
    private double discount;
    @ManyToOne
    @JoinColumn(name = "accountID",nullable = false)
    private Account account;
    //Quan hệ 1 nhiều tới bảng order
    @OneToMany(mappedBy = "voucher", cascade = CascadeType.ALL)
    @JsonIgnore
    @JsonManagedReference
    private List<Orders> orders;
}
