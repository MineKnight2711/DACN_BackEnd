package com.example.dacn.modules.delivery.dto;

import com.example.dacn.entity.Orders;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class DeliveryDetailsDTO
{
    private Orders order;
}
