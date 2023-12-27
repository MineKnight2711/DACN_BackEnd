package com.example.dacn.modules.delivery.dto;

import com.example.dacn.entity.Delivery;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class DeliveryWithDetailsDTO
{
    private Delivery delivery;
    private DeliveryDetailsDTO details;
}
