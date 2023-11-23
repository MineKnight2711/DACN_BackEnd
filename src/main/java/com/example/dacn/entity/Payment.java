package com.example.dacn.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@Entity(name="Payment")
@Table(name="Payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentID;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "info")
    private String info;

    @Column(name = "status")
    private String status;

    @Column(name = "amount")
    private double amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="paid_time")
    private Date paidTime;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL)
    private List<Orders> orders;

}
