package com.example.dacn.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
@Entity(name = "PaymentDetails")
@Table(name = "PaymentDetails")
public class PaymentDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentDetailsId;

    @ManyToOne
    @JoinColumn(name = "orderID",nullable = false)
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "paymentID",nullable = false)
    private Payment payment;

    @Column(name = "info")
    private String info;

    @Column(name = "status")
    private String status;

    @Column(name = "amount")
    private double amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="paid_time")
    private Date paidTime;
}
