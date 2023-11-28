package com.example.dacn.modules.orders.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class OrderDishDTO
{
    private String dishId;
    private int quantity;
}
