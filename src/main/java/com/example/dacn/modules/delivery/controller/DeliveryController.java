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
    @GetMapping("/check-order/{orderId}")
    public ResponseModel checkOrder(@PathVariable("orderId") String orderId)
    {
        return deliveryService.checkOrder(orderId);
    }
    @PostMapping
    public ResponseModel acceptedOrder(@RequestParam("orderId") String orderId,
                                       @RequestParam("accountId") String accountId)
    {
        return deliveryService.asignOrder(orderId,accountId);
    }
    @PostMapping("/complete-delivery/{deliveryId}")
    public ResponseModel acceptedOrder(@PathVariable("deliveryId") String deliveryId)
    {
        return deliveryService.completeDelivery(deliveryId);
    }
}
