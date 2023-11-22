package com.example.dacn.modules.orders.service;

import com.example.dacn.entity.*;
import com.example.dacn.entity.ids.OrderDetailsId;
import com.example.dacn.modules.account.service.AccountService;
import com.example.dacn.modules.dish.service.DishService;
import com.example.dacn.modules.orders.dto.OrdersDTO;
import com.example.dacn.modules.orders.repository.OrderDetailRepository;
import com.example.dacn.modules.orders.repository.OrdersRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private DishService dishService;

    public ResponseModel createOrder(String accountID, OrdersDTO ordersDTO) {
        try {
            Account acc = accountService.findById(accountID);
            if (acc == null) {
                return new ResponseModel("AccountNotFound", "Không tìm thấy tài khoản");
            }
            Orders newOrder=ordersDTO.toEntity();
            newOrder.setAccount(acc);
            ordersRepository.save(newOrder);
            String newOrderId=ordersRepository.findLatestOrderId();
            OrderDetailsId newOrderDetailsId=new OrderDetailsId();
            OrderDetail newOrderDetail=new OrderDetail();
            for(String dishid : ordersDTO.getDishes()){
                Dish dish = dishService.findById(dishid);
                if(dish!=null)
                {

                    newOrderDetailsId.setOrder_id(newOrderId);
                    newOrderDetailsId.setDish_id(dish.getDishID());


                    newOrderDetail.setOrderDetailID(newOrderDetailsId);
                    newOrderDetail.setOrder(newOrder);
                    newOrderDetail.setDish(dish);
                    newOrderDetail.setAmount(ordersDTO.getDishes().size());
                    newOrderDetail.setPrice(dish.getPrice()*ordersDTO.getDishes().size());
                    orderDetailRepository.save(newOrderDetail);
                }

            }
            return new ResponseModel("Success", newOrderDetail);
//            Orders newOrder=ordersDTO.toEntity();

//            newOrder.setOrderDate(new Date());
//            newOrder
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return new ResponseModel("Fail", "Lỗi chưa xác định!");
        }
    }
//
//
//    }
}
