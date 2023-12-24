package com.example.dacn.modules.account.controller;

import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.account.dto.AccountDTO;
import com.example.dacn.modules.account.dto.UserDTO;
import com.example.dacn.modules.account.service.AccountService;
import com.example.dacn.modules.account.service.VertificationService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import java.io.IOException;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private VertificationService vertificationService;
    @PostMapping
    public ResponseModel createAccount(@ModelAttribute AccountDTO dto)
    {
        return accountService.createAccount(dto);
    }
    @PostMapping("/create-staff")
    public ResponseModel createNewStaff(@RequestParam("image") MultipartFile image, @ModelAttribute AccountDTO dto)
    {
        return accountService.createNewStaff(image,dto);
    }
    @PostMapping("/create-user")
    public ResponseModel sendVerificationEmail(@ModelAttribute UserDTO dto) {
        ResponseModel result = vertificationService.createEmailVerificationLink(dto.getEmail(), dto.getPassword());
        if(result.getMessage().equals("Success"))
        {
            return vertificationService.sendVerificationLinkToEmail(result.getData().toString(),dto.getEmail());
        }
        return new ResponseModel("Fail","Không thể tạo tài khoản mới, có lỗi xảy ra");
    }
    @PutMapping("/change-email/{accountId}")
    public ResponseModel updateEmail(@PathVariable("accountId") String accountId,@RequestParam("email") String email, @RequestParam("newEmail") String newEmail)
    {
        ResponseModel response=vertificationService.updateUserEmail(accountId,email, newEmail);
        if(response.getMessage().equals("Success"))
        {
            return vertificationService.sendVerificationLinkToEmail(response.getData().toString(),newEmail);
        }
        return new ResponseModel("Fail","Có lỗi xảy ra");
    }
    @GetMapping("/verifiedEmail/{accountId}")
    public ModelAndView verifyEmailRedirect(@PathVariable("accountId") String accountId,@RequestParam("newEmail") String newEmail) {

        ModelAndView model = new ModelAndView();
        model.setViewName("email-verified");
        model.addObject("email", newEmail);
        accountService.changeEmail(accountId,newEmail);
        return model;
    }


    @GetMapping("/{accountId}")
    public ResponseModel getAccountById(@PathVariable String accountId)
    {
        return accountService.getAccountById(accountId);
    }
    @GetMapping("/get-all-admin/{role}")
    public ResponseModel getAllAdmin(@PathVariable("role") String role)
    {
        return accountService.getAllAdminAccount(role);
    }
    @PutMapping("change-password/{email}")
    public ResponseModel changePassword(
            @PathVariable("email") String email,
            @RequestParam("newPassword") String newPassword)
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
    @GetMapping("/get-by-email/{email}")
    public ResponseModel login(@PathVariable String email)
    {
        return accountService.login(email);
    }
    @PutMapping("/update-image/{accountId}")
    public ResponseModel changeImage(
            @PathVariable("accountId") String accountId,
            @RequestParam("image") MultipartFile image)
    {
        System.out.println("accountId"+accountId);
        return accountService.changeImage(accountId, image);
    }
    @PutMapping("/update-account")
    public ResponseModel updateAccount(
            @ModelAttribute AccountDTO dto)
    {
        return accountService.updateAccount(dto);
    }
    @PutMapping("/update-account-points")
    public ResponseModel updateAccountpoints(
            @RequestParam("accountId") String accountId,@RequestParam("orderTotal") double orderTotal)
    {
        return accountService.updateAccountPoints(accountId,orderTotal);
    }
    @PostMapping("/sign-in")
    public ResponseModel signIn(@ModelAttribute UserDTO userDTO) throws IOException
    {
        return accountService.signInUser(userDTO);
    }
    @PostMapping("/get-user-info")
    public ResponseModel lookupUser(@RequestParam("email") String email, @RequestParam("idToken") String idToken)
    {
        return accountService.lookupUserByEmail(email, idToken);
    }
    @PostMapping("/sign-out/{userId}")
    public ResponseModel signOut(@PathVariable("userId") String userId) {
        return accountService.signOutUser(userId);
    }
}
