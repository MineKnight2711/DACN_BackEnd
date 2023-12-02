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

            if(savedOrder==null)
            {
                return catchTransactionError("SaveOrderFail");
            }
            detailService.saveOrderDetails(savedOrder,dto.getOrdersDTO().getDishes());
            PaymentDetails savedPaymentDetails = paymentService.savePayment(savedOrder,dto.getPaymentDetailsDTO());
            if(savedPaymentDetails==null)
            {
                return catchTransactionError("SavePaymentFail");
            }
            PaymentRequestBody paymentRequestBody=dto.getPaymentRequestBody();
            paymentRequestBody.setOrderCode(savedPaymentDetails.getPaymentDetailsId());
            PaymentResponse response= payOSService.getPaymentLink(paymentRequestBody);
            if(response.getCode().equals("231"))
            {

                return catchTransactionError("OrderCodeExisted");

            }
            return new ResponseModel("Success",response);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return catchTransactionError("");
        }


    }
    private ResponseModel catchTransactionError(String error){
        switch (error) {
            case "SaveOrderFail":
                return new ResponseModel("SaveOrderFail","Lỗi khi lưu đơn hàng!");
            case "SavePaymentFail":
                return new ResponseModel("SavePaymentFail","Lỗi khi lưu giao dịch!");
            case "OrderCodeExisted":
                return new ResponseModel("OrderCodeExisted","Mã giao địch đã tồn tại trên hệ thống payos!");
            default:
                return new ResponseModel("Unknown","Lỗi chưa xác định");
        }
    }
}
