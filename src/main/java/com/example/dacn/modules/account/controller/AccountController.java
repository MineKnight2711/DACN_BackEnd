package com.example.dacn.modules.account.controller;

import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.account.dto.AccountDTO;
import com.example.dacn.modules.account.dto.UserDTO;
import com.example.dacn.modules.account.service.AccountService;
import com.example.dacn.modules.account.service.VertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.IOException;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private VertificationService vertificationService;

    private JavaMailSender mailSender;
    @PostMapping
    public ResponseModel createAccount(@ModelAttribute AccountDTO dto)
    {
        return accountService.createAccount(dto);
    }

    @PostMapping("/create-user")
    public ResponseModel sendVerificationEmail(@ModelAttribute UserDTO dto) {
        ResponseModel result = vertificationService.createEmailVerificationLink(dto.getEmail(), dto.getPassword());
        return vertificationService.sendVerificationLinkToEmail(result.getData().toString(),dto.getEmail());
    }
    @GetMapping("/{accountId}")
    public ResponseModel getAccountById(@PathVariable String accountId)
    {
        return accountService.getAccountById(accountId);
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
    @PutMapping("/update-image/{accountId}")
    public ResponseModel changeImage(
            @PathVariable("accountId") String accountId,
            @RequestParam("image") MultipartFile image) {
        System.out.println("accountId"+accountId);
        return accountService.changeImage(accountId, image);
    }
    @PutMapping("/update-account")
    public ResponseModel updateAccount(
            @ModelAttribute AccountDTO dto) {
        return accountService.updateAccount(dto);
    }
    @PostMapping("/sign-in")
    public ResponseModel managerSignIn(@ModelAttribute UserDTO userDTO) throws IOException {
        return accountService.signInUser(userDTO);
    }
    @PostMapping("/get-user-info")
    public ResponseModel lookupUser(@RequestParam("email") String email, @RequestParam("idToken") String idToken) {
        return accountService.lookupUserByEmail(email, idToken);
    }
    @PostMapping("/sign-out/{userId}")
    public ResponseModel signOut(@PathVariable("userId") String userId) {
        return accountService.signOutUser(userId);
    }
}
