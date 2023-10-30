package com.example.dacn.modules.cart.dto;

import com.example.dacn.entity.Cart;
import com.example.dacn.entity.Dish;
import lombok.*;
@Data
@Getter
@Setter
public class CartDTO {
    private String dishID;
    private String accountID;
    private int quantity;
    private double total;

    public Cart toEntity() {
        Cart cart = new Cart();
        cart.setQuantity(this.quantity);
        cart.setTotal(this.total);
        return cart;
    }
}
