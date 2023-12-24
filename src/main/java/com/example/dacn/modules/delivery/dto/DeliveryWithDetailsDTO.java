package com.example.dacn.modules.delivery.dto;

import com.example.dacn.entity.Delivery;
import com.example.dacn.entity.DeliveryDetails;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Data
@Getter
@Setter
public class DeliveryWithDetailsDTO
{
    private Delivery delivery;
    private List<DeliveryDetailsDTO> details;
}
