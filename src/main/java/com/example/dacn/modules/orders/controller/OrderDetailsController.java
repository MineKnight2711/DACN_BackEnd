package com.example.dacn.modules.orders.controller;

import com.example.dacn.entity.Orders;
import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.orders.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order-details")
public class OrderDetailsController
{
    @Autowired
    private OrderDetailsService orderDetailsService;
    @GetMapping("/{orderId}")
    public ResponseModel getAllOrderDetailByOrderId(@PathVariable("orderId") String orderId) {
        return orderDetailsService.getOrderDetails(orderId);
    }
}
