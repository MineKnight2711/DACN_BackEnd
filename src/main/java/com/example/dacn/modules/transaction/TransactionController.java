package com.example.dacn.modules.transaction;

import com.example.dacn.entity.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController
{
    @Autowired
    private TransactionService transactionService;
    @PostMapping
    public ResponseModel transaction(@RequestBody TransactionDTO dto)
    {
        return transactionService.beginTransaction(dto);
    }
}
