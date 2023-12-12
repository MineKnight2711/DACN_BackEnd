package com.example.dacn.modules.voucher_account.controller;

import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.voucher_account.dto.AccountVoucherDTO;
import com.example.dacn.modules.voucher_account.service.AccountVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/voucher-account")
public class AccountVoucherController
{

    @Autowired
    private AccountVoucherService accountVoucherService;
    @GetMapping("/{accountId}")
    public ResponseModel getAccountVoucherByAccountId(@PathVariable("accountId") String accountId)
    {
        return accountVoucherService.getAccountVoucherByAccountId(accountId);
    }
    @PostMapping
    public ResponseModel createAccountVoucher(@ModelAttribute AccountVoucherDTO dto)
    {
        return accountVoucherService.createaccounVoucher(dto);
    }
}
