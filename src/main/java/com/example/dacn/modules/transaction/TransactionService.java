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
    final String statusPending="Chờ thanh toán";
    final String statusPaid="Đã thanh toán";
    final String cancelUrl="http://localhost:6969/api/transaction/cancel";
    final String returnUrl="http://localhost:6969/api/transaction/success";

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
            dto.getOrdersDTO().setStatus(statusPending);
            Orders savedOrder = orderService.createOrder(dto.getAccountId(),dto.getOrdersDTO());

            if(savedOrder==null)
            {
                return catchTransactionError("SaveOrderFail");
            }
            detailService.saveOrderDetails(savedOrder,dto.getOrdersDTO().getDishes());
            dto.getPaymentDetailsDTO().setStatus(statusPending);
            dto.getPaymentDetailsDTO().setInfo("Thanh toán đơn hàng - "+savedOrder.getOrderID());
            PaymentDetails savedPaymentDetails = paymentService.savePayment(savedOrder,dto.getPaymentDetailsDTO());
            if(savedPaymentDetails==null)
            {
                return catchTransactionError("SavePaymentFail");
            }
            PaymentRequestBody paymentRequestBody=dto.getPaymentRequestBody();
            paymentRequestBody.setCancelUrl(cancelUrl);
            paymentRequestBody.setReturnUrl(returnUrl);
            paymentRequestBody.setDescription("ĐTT"+savedOrder.getOrderID());
            paymentRequestBody.setOrderCode(savedPaymentDetails.getPaymentDetailsId());
            PaymentResponse response= payOSService.getPaymentLink(paymentRequestBody);
            if(response.getCode().equals("231"))
            {
                return catchTransactionError("OrderCodeExisted");
            }
            TransactionResponse transactionResponse=new TransactionResponse();
            transactionResponse.setOrderId(savedOrder.getOrderID());
            transactionResponse.setPaymentDetailsId(savedPaymentDetails.getPaymentDetailsId());
            transactionResponse.setPaymentResponse(response);
            return new ResponseModel("Success",transactionResponse);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return catchTransactionError("");
        }


    }
    @Transactional
    public ResponseModel updateTransaction(String orderId,Long paymentDetailsId) {
        try
        {
            Orders orders=orderService.findById(orderId);
            if(orders!=null)
            {
                PaymentDetails paymentDetails=paymentService.findDetailById(paymentDetailsId);
                if(paymentDetails!=null)
                {
                    orders.setStatus(statusPaid);
                    paymentDetails.setStatus(statusPaid);
                    Orders savedOrder= orderService.updateOrder(orders);
                    PaymentDetails savedPaymentDetails=paymentService.updatePaymentDetails(paymentDetails);
                    if (savedOrder!=null&&savedPaymentDetails!=null)
                    {
                        return new ResponseModel("Success",statusPaid);
                    }
                    return new ResponseModel("Fail","Có lỗi xảy ra");
                }
                return new ResponseModel("PaymentDetailsNotFound","Không tìm thấy chi tiết thanh toán!");
            }
            return new ResponseModel("PaymentDetailsNotFound","Không tìm thấy đơn hàng !");
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
