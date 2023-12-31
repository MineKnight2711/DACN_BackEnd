package com.example.dacn.modules.cart.dto;

import com.example.dacn.entity.Account;
import com.example.dacn.entity.Cart;
import com.example.dacn.entity.Dish;
import lombok.*;
@Data
@Getter
@Setter
public class CartDTO
{
    private String dishID;
    private String accountID;
    private int quantity;
    public Cart toEntity()
    {
        Cart cart = new Cart();
        cart.setQuantity(this.quantity);
        return cart;
    }
}
