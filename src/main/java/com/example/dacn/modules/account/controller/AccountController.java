package com.example.dacn.modules.account.controller;

import com.example.dacn.entity.Account;
import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.account.dto.AccountDTO;
import com.example.dacn.modules.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @PostMapping
    public ResponseModel createAccount(@ModelAttribute AccountDTO dto)
    {
        System.out.println(dto.getPassword());
        return accountService.createAccount(dto);
    }
    @GetMapping("/login/{email}")
    public ResponseModel login(@PathVariable String email,@RequestParam String password)
    {
        return accountService.login(email,password);
    }

}
