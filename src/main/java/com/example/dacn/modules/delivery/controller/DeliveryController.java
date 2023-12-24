package com.example.dacn.modules.delivery.controller;

import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.delivery.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/delivery")
public class DeliveryController
{
    @Autowired
    private DeliveryService deliveryService;
    @GetMapping("/get-by-accountId/{accountId}")
    public ResponseModel getByAccountId(@PathVariable("accountId") String accountId)
    {
        return deliveryService.getDeliveryByAccountId(accountId);
    }
    @PostMapping
    public ResponseModel acceptedOrder(@RequestParam("orderId") String orderId,
                                       @RequestParam("accountId") String accountId)
    {
        return deliveryService.acceptOrder(orderId,accountId);
    }
}
