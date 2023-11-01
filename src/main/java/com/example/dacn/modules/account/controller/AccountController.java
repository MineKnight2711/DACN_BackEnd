package com.example.dacn.modules.account.controller;

import com.example.dacn.entity.Account;
import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.account.dto.AccountDTO;
import com.example.dacn.modules.account.dto.ChangePassDTO;
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
    @GetMapping("/{accountId}")
    public ResponseModel getAccountById(@PathVariable String accountId)
    {
        return accountService.getAccountById(accountId);
    }
    @GetMapping("/{email}")
    public ResponseModel getAccountByEmail(@PathVariable String email)
    {
        return accountService.getAccountById(email);
    }
    @PutMapping("/{email}")
    public ResponseModel changePassword(
            @PathVariable("email") String email,
            @RequestParam("newPassword") String newPassword
    )
    {
        return accountService.changePassword(email, newPassword);
    }
    @PostMapping("reset-password/{email}")
    public ResponseModel resetPassword(
            @PathVariable("email") String email
    )
    {
        return accountService.sendPasswordResetLink(email);
    }
    @GetMapping("/login-by-email/{email}")
    public ResponseModel login(@PathVariable String email)
    {
        return accountService.login(email);
    }
    @PutMapping("/updateImage/{accountId}")
    public ResponseModel changeImage(
            @PathVariable("accountId") String accountId,
            @RequestParam String newImageUrl
    ) {
        return accountService.changeImage(accountId, newImageUrl);
    }
//    @PutMapping("/change-password")
//    public ResponseModel changePassword(@ModelAttribute ChangePassDTO changePassDTO)
//    {
//        return accountService.changePassword(changePassDTO);
//    }
}
