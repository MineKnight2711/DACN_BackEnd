package com.example.dacn.entity;

import com.example.dacn.entity.ids.OrderDetailsId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "order_id",nullable = false,insertable=false,updatable=false)
    private Orders order;
    @Column(name = "amount")
    private int amount;

    @Column(name = "price")
    private double price;
}
