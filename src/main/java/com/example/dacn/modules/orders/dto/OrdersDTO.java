package com.example.dacn.modules.orders.dto;


import com.example.dacn.entity.Orders;
import com.fasterxml.jackson.annotation.JsonProperty;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
public class OrdersDTO {

    private String orderID;
    private String status;
    private String voucherId;
    private int quantity;
    @JsonProperty("orderDate")

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;
    private String deliveryInfo;

    private List<OrderDishDTO> dishes;
    public Orders toEntity() {
        Orders orders = new Orders();
        orders.setOrderID(this.orderID);
        orders.setStatus(this.status);
        int quantity=0;
        for(OrderDishDTO dish : this.dishes)
        {
            quantity+=dish.getQuantity();

        }
        orders.setQuantity(quantity);
        orders.setOrderDate(this.orderDate);
        orders.setDeliveryInfo(this.deliveryInfo);
        return orders;
    }
}
