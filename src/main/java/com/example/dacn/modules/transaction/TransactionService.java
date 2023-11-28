package com.example.dacn.modules.transaction;

import com.example.dacn.entity.Orders;
import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.orders.service.OrderDetailsService;
import com.example.dacn.modules.orders.service.OrdersService;
import com.example.dacn.modules.payment.service.PaymentService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService
{
    @Autowired
    private OrdersService orderService;
    @Autowired
    private OrderDetailsService detailService;
    @Autowired
    private PaymentService paymentService;
    @Transactional
    public ResponseModel beginTransaction(TransactionDTO dto) {
        try
        {

            Orders savedOrder = orderService.createOrder(dto.getAccountId(),dto.getOrdersDTO());

            System.out.println("Order ::"+savedOrder.getOrderID());
            if(savedOrder==null)
            {
                return new ResponseModel("SaveOrderFail","Có lỗi xảy ra khi lưu order");
            }
            detailService.saveOrderDetails(savedOrder,dto.getOrdersDTO().getDishes());
            System.out.println("Saved OrderId ::"+savedOrder.getOrderID());
            paymentService.savePayment(savedOrder,dto.getPaymentDetailsDTO());
            return new ResponseModel("Success",savedOrder);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return new ResponseModel("Fail","Có lỗi xảy ra");
        }


    }
}
