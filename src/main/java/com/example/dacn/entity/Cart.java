package com.example.dacn.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
@Getter
@Setter
@Entity(name = "Cart")
@Table(name = "Cart")
//Đây là bảng nhiều nhiều
public class Cart {
    @Id
    private String cartID;

    @ManyToOne

    @JoinColumn(name = "dishID",nullable = false)
    private Dish dish;

    @ManyToOne

    @JoinColumn(name = "accountID",nullable = false)
    private Account account;


    @Column(name = "quantity")
    private int quantity;

    @Column(name = "total")
    private double total;
}








