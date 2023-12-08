package com.example.dacn.modules.transaction.controller;

import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.apikey.ApiKeyService;
import com.example.dacn.modules.transaction.dto.CODTransactionDTO;
import com.example.dacn.modules.transaction.service.TransactionService;
import com.example.dacn.modules.transaction.dto.VietQRTransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController
{
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ApiKeyService apiKeyService;
    @PostMapping("/VietQR-PAYOS")
    public ResponseModel vietQRTransaction(@RequestHeader(name="X-Client-Id") String clientId,
                                           @RequestHeader(name="X-Api-Key") String apiKey,
                                           @RequestBody VietQRTransactionDTO dto)
    {
        if(apiKeyService.isApiValid(clientId,apiKey))
        {
            return transactionService.performVietQRTransaction(dto);
        }
        return new ResponseModel("401","Bạn không có quyền sử dụng API này!");
    }
    @PostMapping("/COD")
    public ResponseModel codTransaction(@RequestHeader(name="X-Client-Id") String clientId,
                                        @RequestHeader(name="X-Api-Key") String apiKey,
                                        @RequestBody CODTransactionDTO dto)
    {
        System.out.println(dto);
        if(apiKeyService.isApiValid(clientId,apiKey))
        {
            return transactionService.performCODTransaction(dto);
        }
        return new ResponseModel("401","Bạn không có quyền sử dụng API này!");
    }
    @PutMapping("/update")
    public ResponseModel updateTransaction(@RequestHeader(name="X-Client-Id") String clientId,
                                           @RequestHeader(name="X-Api-Key") String apiKey,
                                           @RequestParam("orderId") String orderId,
                                           @RequestParam("paymentDetailsId") Long paymentDetailsId)
    {
        if(apiKeyService.isApiValid(clientId,apiKey))
        {
            return transactionService.updateTransaction(orderId,paymentDetailsId);
        }
        return new ResponseModel("401","Bạn không có quyền sử dụng API này!");
    }
    @PutMapping("/cancel")
    public ResponseModel cancelTransaction(@RequestHeader(name="X-Client-Id") String clientId,
                                           @RequestHeader(name="X-Api-Key") String apiKey,
                                           @RequestParam("orderId") String orderId,
                                           @RequestParam("paymentDetailsId") Long paymentDetailsId)
    {
        if(apiKeyService.isApiValid(clientId,apiKey))
        {
            return transactionService.cancelTransaction(orderId,paymentDetailsId);
        }
        return new ResponseModel("401","Bạn không có quyền sử dụng API này!");
    }
}
