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
    @GetMapping("/{accountId}")
    public ResponseModel getOrderByAccountID(@PathVariable("accountId") String accountId)
    { return ordersService.getOrderByAccountID(accountId); }

    @GetMapping("/get-by-orderState/{accountId}")
    public ResponseModel getOrderByOrderState(@PathVariable("accountId") String accountId, @RequestParam("orderState") String orderState)
    { return ordersService.getOrderByOrderState(accountId,orderState); }
    @PostMapping("/{accountId}")
    public ResponseModel createOrder(@PathVariable("accountId") String accountId,
                                     @RequestBody OrdersDTO ordersDTO)
    {
        Orders orders=ordersService.createOrder(accountId,ordersDTO);
        if(orders!=null)
        {
            return new ResponseModel("Result",orders);
        }
        return new ResponseModel("Fail","Không thể tạo đơn hàng");
    }

}
