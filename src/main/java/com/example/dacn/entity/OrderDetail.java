package com.example.dacn.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity(name = "order_detail")
@Table(name = "order_detail")
public class OrderDetail {
    @Id
    private String orderDetailID;
    @ManyToOne
    @JoinColumn(name = "dishID",nullable = false)
    private Dish dish;
    @ManyToOne
    @JoinColumn(name = "orderID",nullable = false)
    private Orders order;
    @Column(name = "amount")
    private int amount;
    @Column(name = "price")
    private double price;
}
