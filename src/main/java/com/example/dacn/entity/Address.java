package com.example.dacn.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity(name="Address")
@Table(name="Address")
public class Address {
    @Id
    private String addressID;
    @Column(name = "ward")
    private String ward;
    @Column(name = "district")
    private String district;
    @Column(name = "province")
    private String province;
    @Column(name = "details")
    private String details;
    @Column(name = "addressName")
    private String addressName;
    @Column(name = "receiverName")
    private String receiverName;
    @Column(name = "receiverPhone")
    private String receiverPhone;
    @Column(name = "defaultAddress")
    private boolean defaultAddress;
    @ManyToOne
    @JoinColumn(name = "accountID")
    private Account account;
}
