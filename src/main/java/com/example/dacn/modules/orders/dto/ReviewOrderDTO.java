package com.example.dacn.modules.orders.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Data
@Getter
@Setter
public class ReviewOrderDTO
{
    private String orderId;
    private Double score;
    private String feedBack;
}
