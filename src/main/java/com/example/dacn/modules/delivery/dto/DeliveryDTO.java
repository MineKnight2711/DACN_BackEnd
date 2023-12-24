package com.example.dacn.modules.delivery.dto;

import com.example.dacn.entity.Delivery;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Getter
@Setter
public class DeliveryDTO
{
    private String deliveryId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateAccepted;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateDelivered;
}
