package com.example.dacn.modules.orders.dto;

import com.example.dacn.entity.Dish;
import com.example.dacn.entity.Orders;
import com.example.dacn.modules.dish.dto.DishDTO;
import com.example.dacn.utils.DatetimeDeserialize;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.criteria.Order;
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
    private int quantity;
    @JsonProperty("orderDate")
    @JsonDeserialize(using = DatetimeDeserialize.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;
    private String deliveryInfo;

    private List<OrderDishDTO> dishes;
    public Orders toEntity() {
        Orders orders = new Orders();
        orders.setOrderID(this.orderID);
        orders.setStatus(this.status);
        for(OrderDishDTO dish : dishes)
        {
            orders.setQuantity(this.quantity+=dish.getQuantity());
        }
        orders.setOrderDate(this.orderDate);
        orders.setDeliveryInfo(this.deliveryInfo);
        return orders;
    }
}
