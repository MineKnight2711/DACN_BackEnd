package com.example.dacn.modules.orders.service;

import com.example.dacn.entity.Account;
import com.example.dacn.entity.Dish;
import com.example.dacn.entity.Orders;
import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.account.service.AccountService;
import com.example.dacn.modules.orders.dto.OrdersDTO;
import com.example.dacn.modules.orders.repository.OrderDetailRepository;
import com.example.dacn.modules.orders.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private AccountService accountService;

//    public ResponseModel createOrder(String accountID, OrdersDTO ordersDTO) {
//        try{
//            Account acc=accountService.findById(accountID);
//            if(acc==null)
//            {
//                return new ResponseModel("AccountNotFound", "Không tìm thấy tài khoản");
//            }
//
//        }
//
//
//    }
}
