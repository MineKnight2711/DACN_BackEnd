package com.example.dacn.modules.orders.service;

import com.example.dacn.entity.*;

import com.example.dacn.modules.account.service.AccountService;

import com.example.dacn.modules.orders.dto.OrdersDTO;

import com.example.dacn.modules.orders.repository.OrdersRepository;

import com.google.gson.Gson;
import jakarta.persistence.criteria.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private AccountService accountService;


    public Orders createOrder(String accountID, OrdersDTO ordersDTO) {
        ordersDTO.setOrderID("");

            Account acc = accountService.findById(accountID);
            if (acc == null) {
                return null;
            }
            Orders newOrder=ordersDTO.toEntity();
            newOrder.setAccount(acc);
            ordersRepository.save(newOrder);
            String newOrderId=ordersRepository.findLatestOrderId();
            newOrder.setOrderID(newOrderId);
            System.out.println("Order id :"+newOrder.getOrderID());
            return newOrder;

    }
    public Orders findById(String orderId) {
        Orders orders=ordersRepository.findById(orderId).orElse(null);
        if(orders!=null){

            return orders;
        }
        return null;
    }
    public Orders updateOrder(Orders order) {
        try{
            Orders orders=ordersRepository.save(order);
            return orders;
        }catch (Exception ex){
            return null;
        }
    }

    public ResponseModel getOrderByAccountID(String accountId)
    {
        List<Orders> ordersList=ordersRepository.findOrdersByAccountId(accountId);
        if(!ordersList.isEmpty())
        {
            return new ResponseModel("Success",ordersList);
        }
        return new ResponseModel("Fail","Tài khoản chưa có đơn hàng!");
    }
    public ResponseModel getOrderByOrderState(String accountId,String orderState)
    {
        List<Orders> ordersList=ordersRepository.findOrdersByOrderState(accountId,orderState);
        if(!ordersList.isEmpty())
        {
            return new ResponseModel("Success",ordersList);
        }
        return new ResponseModel("Fail","Tài khoản chưa có đơn hàng!");
    }
}
