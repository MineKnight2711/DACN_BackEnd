package com.example.dacn.modules.account.controller;

import com.example.dacn.entity.Account;
import com.example.dacn.modules.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @PostMapping
    public Account createAccount(@ModelAttribute Account account)
    {
        return accountService.createAccount(account);
    }
}
