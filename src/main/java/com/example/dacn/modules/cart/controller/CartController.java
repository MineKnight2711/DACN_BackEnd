package com.example.dacn.modules.cart.controller;

import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.cart.dto.CartDTO;
import com.example.dacn.modules.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseModel addToCart(@ModelAttribute CartDTO dto)
    {
        return cartService.addToCart(dto);
    }
}
