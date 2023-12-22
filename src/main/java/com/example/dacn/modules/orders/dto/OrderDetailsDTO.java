package com.example.dacn.modules.orders.dto;

import com.example.dacn.entity.Dish;
import com.example.dacn.entity.OrderDetail;
import com.example.dacn.entity.Orders;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Data
@Getter
@Setter
public class OrderDetailsDTO {
    private Orders order;
    private String paymentMethod;
    private List<DetailsDTO> detailList;
    @Data
    @Getter
    @Setter
    public static class DetailsDTO
    {
        private Dish dish;
        private int amount;
        private double price;
    }
}
