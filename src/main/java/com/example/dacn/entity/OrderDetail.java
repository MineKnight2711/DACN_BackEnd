package com.example.dacn.entity;

import com.example.dacn.entity.ids.OrderDetailsId;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity(name = "OrderDetail")
@Table(name = "OrderDetail")
public class OrderDetail {
    @EmbeddedId
    private OrderDetailsId orderDetailID;


    @ManyToOne
    @JoinColumn(name = "dish_id",nullable = false,insertable=false,updatable=false)
    private Dish dish;

    @ManyToOne
    @JoinColumn(name = "order_id",nullable = false,insertable=false,updatable=false)
    private Orders order;

    @Column(name = "amount")
    private int amount;

    @Column(name = "price")
    private double price;
}
