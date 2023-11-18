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
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private String addressID;
    @Column(name = "address")
    private String address;
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
