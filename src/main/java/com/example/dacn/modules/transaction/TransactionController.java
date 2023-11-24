package com.example.dacn.modules.transaction;

import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.apikey.ApiKeyService;
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
    @PostMapping
    public ResponseModel transaction(@RequestHeader(name="X-Client-Id") String clientId,
                                     @RequestHeader(name="X-Api-Key") String apiKey,@RequestBody TransactionDTO dto)
    {
        if(apiKeyService.isApiValid(clientId,apiKey))
        {
            return transactionService.beginTransaction(dto);
        }
        return new ResponseModel("401","Bạn không có quyền sử dụng API này!");
    }
}
