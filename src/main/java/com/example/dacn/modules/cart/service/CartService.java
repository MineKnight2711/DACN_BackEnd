package com.example.dacn.modules.cart.service;


import com.example.dacn.entity.*;
import com.example.dacn.modules.account.service.AccountService;
import com.example.dacn.modules.cart.dto.CartDTO;
import com.example.dacn.modules.cart.repository.CartRepository;
import com.example.dacn.modules.dish.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private DishService dishService;


    public ResponseModel addToCart(CartDTO dto)
    {
       try {
           Account acc=accountService.findById(dto.getAccountID());
           if(acc!=null)
           {
               Dish dish= dishService.findById(dto.getDishID());
               if(dish!=null)
               {
                   Cart newCartItem=dto.toEntity();
                   newCartItem.setCartID("");
                   newCartItem.setAccount(acc);
                   newCartItem.setDish(dish);
                   cartRepository.save(newCartItem);
                   return new ResponseModel("Success",newCartItem);
               }
               else
               {
                   return new ResponseModel("DishNotFound","Không tìm thấy món ăn");
               }
           }
           else
           {
               return new ResponseModel("AccountNotFound","không tìm thấy tài khoản");
           }
       }
       catch (Exception ex)
       {
           ex.printStackTrace();
           return new ResponseModel("Fail","Lỗi chưa xác định!");
       }
    }
}
