package com.example.dacn.modules.transaction;

import com.example.dacn.entity.Orders;
import com.example.dacn.entity.Payment;
import com.example.dacn.entity.PaymentDetails;
import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.orders.service.OrderDetailsService;
import com.example.dacn.modules.orders.service.OrdersService;
import com.example.dacn.modules.payment.service.PaymentService;
import com.example.dacn.modules.payos.model.PaymentRequestBody;
import com.example.dacn.modules.payos.model.PaymentResponse;
import com.example.dacn.modules.payos.service.PayOSService;
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
    @Autowired
    private PayOSService payOSService;
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
            PaymentDetails savedPamentDetails = paymentService.savePayment(savedOrder,dto.getPaymentDetailsDTO());
            if(savedPamentDetails==null)
            {
                return new ResponseModel("FailToSavePayment","Không thể lưu giao dịch!");
            }
            PaymentRequestBody paymentRequestBody=dto.getPaymentRequestBody();
            paymentRequestBody.setOrderCode(savedPamentDetails.getPaymentDetailsId());
            PaymentResponse response= payOSService.getPaymentLink(paymentRequestBody);

            return new ResponseModel("Success",response);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return new ResponseModel("Fail","Có lỗi xảy ra");
        }


    }
}
