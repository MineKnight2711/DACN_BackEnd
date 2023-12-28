package com.example.dacn.modules.cart.service;


import com.example.dacn.entity.*;
import com.example.dacn.modules.account.service.AccountService;
import com.example.dacn.modules.cart.dto.CartDTO;
import com.example.dacn.modules.cart.repository.CartRepository;
import com.example.dacn.modules.dish.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        Account acc=accountService.findById(dto.getAccountID());
        if(acc!=null)
        {
            Dish dish= dishService.findById(dto.getDishID());
            if(dish!=null)
            {
                if(dish.getInStock()<1)
                {
                    return new ResponseModel("OutOfStock","Món ăn hết hàng!");
                }
                Cart newCartItem=dto.toEntity();
                newCartItem.setCartID("");
                newCartItem.setAccount(acc);
                newCartItem.setDish(dish);
                newCartItem.setTotal(dish.getPrice()*dto.getQuantity());
                Cart isUniqueCart=cartRepository.findCartByAccountAndDishId(dto.getAccountID(),dto.getDishID());
                if(isUniqueCart!=null){
                    //Save giỏ hàng đã tồn tại món ăn của 1 tài khoản
                    int newQuantity=isUniqueCart.getQuantity()+dto.getQuantity();
                    isUniqueCart.setQuantity(newQuantity);
                    isUniqueCart.setTotal(newQuantity*isUniqueCart.getDish().getPrice());
                    cartRepository.save(isUniqueCart);
                    return new ResponseModel("UpdatedCart",newCartItem);
                }
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
    public ResponseModel updateCart(String cartId,int newCartQuantity)
    {
        try
        {
            Cart cart=cartRepository.findById(cartId).orElse(null);

            if(cart!=null)
            {
                cart.setQuantity(newCartQuantity);
                cart.setTotal(newCartQuantity*cart.getDish().getPrice());
                cartRepository.save(cart);
                return new ResponseModel("UpdatedCart",cart);
            }
            return new ResponseModel("NotCartToUpdate","Không tìm thấy sản phẩm để cập nhật!");
        }
        catch (Exception ex)
        {
            return new ResponseModel("Fail","Lỗi chưa xác định");
        }
    }

    public ResponseModel getByAccountId(String cartId)
    {
        List<Cart> carts=cartRepository.findCartByAccountId(cartId);
        if(!carts.isEmpty())
        {
            return new ResponseModel("Success",carts);
        }
        return new ResponseModel("NoItem","Giỏ hàng trống không :((");
    }

    public ResponseModel deleteCart(String cartId)
    {
        Cart cart=cartRepository.findById(cartId).orElse(null);

        if(cart!=null)
        {
            cartRepository.delete(cart);
            return new ResponseModel("Success",cart);
        }
        return new ResponseModel("NoCart","Không thể xoá món ăn trong giỏ hàng");
    }
    public ResponseModel deleteMultipleCart(List<String> listCartId)
    {
        List<Cart> carts=new ArrayList<>();
        for(String cartId : listCartId)
        {
            Cart cart=cartRepository.findById(cartId).orElse(null);

            if(cart!=null)
            {
                cartRepository.delete(cart);
                carts.add(cart);
            }
        }

        return new ResponseModel("Success",carts);
    }
}
