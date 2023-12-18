package com.example.dacn.modules.transaction.service;

import com.example.dacn.entity.Orders;
import com.example.dacn.entity.PaymentDetails;
import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.account.service.AccountService;
import com.example.dacn.modules.orders.service.OrderDetailsService;
import com.example.dacn.modules.orders.service.OrdersService;
import com.example.dacn.modules.payment.service.PaymentService;
import com.example.dacn.modules.payos.model.PaymentRequestBody;
import com.example.dacn.modules.payos.model.PaymentResponse;
import com.example.dacn.modules.payos.service.PayOSService;
import com.example.dacn.modules.transaction.OrderStatus;
import com.example.dacn.modules.transaction.dto.CODTransactionDTO;
import com.example.dacn.modules.transaction.dto.VietQRTransactionDTO;
import com.example.dacn.modules.transaction.dto.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService
{

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
    public ResponseModel performVietQRTransaction(VietQRTransactionDTO dto) {
        try
        {
            dto.getOrdersDTO().setStatus(OrderStatus.STATUSPENDING);
            Orders savedOrder = orderService.createOrder(dto.getAccountId(),dto.getOrdersDTO());

            if(savedOrder==null)
            {
                return catchTransactionError("SaveOrderFail");
            }
            detailService.saveOrderDetails(savedOrder,dto.getOrdersDTO().getDishes());
            dto.getPaymentDetailsDTO().setStatus(OrderStatus.STATUSPENDING);
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
                return rollBackTransaction(savedOrder.getOrderID(), savedPaymentDetails.getPaymentDetailsId());
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
    private ResponseModel rollBackTransaction(String orderId,Long paymentDetailsId)
    {
        if(orderService.deleteOrder(orderId)&&paymentService.cancelPayment(paymentDetailsId))
        {
            return catchTransactionError("TransactionFail") ;
        }
        return catchTransactionError("RollBackFail");
    }
    @Transactional
    public ResponseModel performCODTransaction(CODTransactionDTO dto)
    {
        try
        {
            dto.getOrdersDTO().setStatus(OrderStatus.STATUSPENDING);
            Orders savedOrder = orderService.createOrder(dto.getAccountId(),dto.getOrdersDTO());

            if(savedOrder==null)
            {
                return catchTransactionError("SaveOrderFail");
            }
            detailService.saveOrderDetails(savedOrder,dto.getOrdersDTO().getDishes());
            dto.getPaymentDetailsDTO().setStatus(OrderStatus.STATUSPENDING);
            dto.getPaymentDetailsDTO().setInfo("Thanh toán đơn hàng - "+savedOrder.getOrderID());
            PaymentDetails savedPaymentDetails = paymentService.savePayment(savedOrder,dto.getPaymentDetailsDTO());
            if(savedPaymentDetails==null)
            {
                return catchTransactionError("SavePaymentFail");
            }
            TransactionResponse transactionResponse=new TransactionResponse();
            transactionResponse.setOrderId(savedOrder.getOrderID());
            transactionResponse.setPaymentDetailsId(savedPaymentDetails.getPaymentDetailsId());
            return new ResponseModel("Success",transactionResponse);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return catchTransactionError("");
        }
    }
    @Transactional
    public ResponseModel cancelTransaction(String orderId,Long paymentDetailsId) {
        try
        {
            if(orderService.cancelOrder(orderId)&&paymentService.cancelPayment(paymentDetailsId))
            {
                return new ResponseModel("Success",OrderStatus.STATUSCANCEL);
            }
            return new ResponseModel("Fail","Có lỗi xảy ra");
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
            if(orderService.updateOrder(orderId)&&paymentService.updatePaymentDetails(paymentDetailsId))
            {
                return new ResponseModel("Success",OrderStatus.STATUSPAID);
            }
            return new ResponseModel("Fail","Có lỗi xảy ra");
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
            case "TransactionFail":
                return new ResponseModel("TransactionFail","Đã tồn tại mã giao dịch trên hệ thống PAYOS");
            case "RollBackFail":
                return new ResponseModel("RollBackFail","Huỷ giao dịch thất bại!");
            case "SavePaymentFail":
                return new ResponseModel("SavePaymentFail","Lỗi khi lưu giao dịch!");
            case "OrderCodeExisted":
                return new ResponseModel("OrderCodeExisted","Mã giao địch đã tồn tại trên hệ thống payos!");
            case "CannotCreateCheckoutLink":
                return new ResponseModel("CannotCreateCheckoutLink","Không thể tạo link thanh toán!");
            default:
                return new ResponseModel("Unknown","Lỗi chưa xác định");
        }
    }


}
