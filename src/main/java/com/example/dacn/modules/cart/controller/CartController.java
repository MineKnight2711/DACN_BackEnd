package com.example.dacn.modules.cart.controller;

import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.cart.dto.CartDTO;
import com.example.dacn.modules.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController
{
    @Autowired
    private CartService cartService;
    @GetMapping("/get-by-accountId/{accountId}")
    public ResponseModel getCartByAccountId(@PathVariable("accountId") String cartId) { return cartService.getByAccountId(cartId); }

    @PostMapping
    public ResponseModel addToCart(@ModelAttribute CartDTO dto)
    {
        return cartService.addToCart(dto);
    }

    @PutMapping("/{cartId}")
    public ResponseModel updateCart(@PathVariable("cartId") String cartId, @RequestParam("newQuantity") int newQuantity)
    {
        return cartService.updateCart(cartId,newQuantity);
    }
    @DeleteMapping("/{cartId}")
    public ResponseModel deleteCart(@PathVariable("cartId") String cartId)
    {
        return cartService.deleteCart(cartId);
    }
    @DeleteMapping("/delete-many-cart")
    public ResponseModel deleteMultipleCart(@RequestBody List<String> cartId)
    {
        return cartService.deleteMultipleCart(cartId);
    }
}
