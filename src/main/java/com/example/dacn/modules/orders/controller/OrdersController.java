package com.example.dacn.modules.orders.controller;

import com.example.dacn.entity.Orders;
import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.orders.dto.OrdersDTO;
import com.example.dacn.modules.orders.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    @PostMapping("/{accountId}")
    public ResponseModel createOrder(@PathVariable("accountId") String accountId,
                                     @RequestBody OrdersDTO ordersDTO) {
        return ordersService.createOrder(accountId,ordersDTO);
    }
}
