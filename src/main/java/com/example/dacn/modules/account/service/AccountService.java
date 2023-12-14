package com.example.dacn.modules.account.service;
import com.example.dacn.entity.Account;
import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.account.dto.AccountDTO;

import com.example.dacn.modules.account.dto.UserDTO;
import com.example.dacn.modules.account.dto.UserLookUpResponse;
import com.example.dacn.modules.account.dto.UserSignInResponse;
import com.example.dacn.modules.account.repository.AccountRepository;
import com.example.dacn.utils.Constant;
import com.example.dacn.utils.DataConvert;


import com.example.dacn.utils.ImageService;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zaxxer.hikari.util.ClockSource;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Pattern;


@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ImageService imageService;
    @Autowired
    private FirebaseAuth firebaseAuth;
    @Autowired
    private Gson gson;


    public ResponseModel getAccountById(String id)
    {
        Account account=accountRepository.findById(id).orElse(null);
        if(account==null)
        {
            return new ResponseModel("NoAccount",null);
        }
        return new ResponseModel("Success",account);

    }

    public ResponseModel createAccount(AccountDTO dto)
    {
        dto.setAccountID("");
        if(dto.getBirthday()!=null)
        {
            dto.setBirthday(DataConvert.parseBirthday(dto.getBirthday()));
        }
        else
        {
            dto.setBirthday(null);
        }
        try
        {
            if(accountRepository.findByEmail(dto.getEmail())!=null)
            {
                return new ResponseModel("EmailAlreadyExist",dto.getEmail());
            }
            dto.setImageUrl(Constant.DEFAULT_AVATAR);
            dto.setRole("User");
            dto.setPoints(0);
            dto.setLifetimePoints(0);
            dto.setTier("Bronze");

            Account result= accountRepository.save(dto.toEntity());
            return new ResponseModel("Success",result);
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
            return new ResponseModel("SomethingWrong",null);
        }

    }

//    private boolean validatePassword(String passwordHashed,String passwordInput)
//    {
//        return BCrypt.checkpw(passwordInput,passwordHashed);
//    }
//    public ResponseModel changePassword(ChangePassDTO changePassDTO)
//    {
//        String passwordHashed=accountRepository.getPasswordByEmail(changePassDTO.getEmail());
//        if(passwordHashed==null||passwordHashed=="")
//        {
//            return new ResponseModel("AccountNotFound",null);
//        }
//        if(BCrypt.checkpw(changePassDTO.getOldPassword(),passwordHashed))
//        {
//            Account acc=accountRepository.findByEmail(changePassDTO.getEmail());
//            String salt = BCrypt.gensalt();
//            String newHashedPassword= BCrypt.hashpw(changePassDTO.getNewPassword(), salt);
//            accountRepository.save(acc);
//            return new ResponseModel("Success",null);
//        }
//        else
//        {
//            return new ResponseModel("WrongPass",null);
//        }
//    }
    public ResponseModel login(String email)
    {
        Account account= accountRepository.findByEmail(email);
        if(account==null)
        {
            return new ResponseModel("AccountNotFound",null);
        }
        return new ResponseModel("Success",account);
    }
    public ResponseModel lookupUserByEmail(String email, String idToken) {
        try {
            String url = "https://identitytoolkit.googleapis.com/v1/accounts:lookup?key=" + Constant.FIREBASE_API_KEY;
            HttpClient httpClient = HttpClients.createDefault();

            // Create an HTTP POST request
            HttpPost httpPost = new HttpPost(url);

            String requestBody = "{\"idToken\": \"" + idToken + "\"}";
            httpPost.setEntity(new StringEntity(requestBody, "application/json", "UTF-8"));

            // Execute the request
            HttpResponse response = httpClient.execute(httpPost);

            // For example, you can use the EntityUtils to read the response content
            String responseContent = EntityUtils.toString(response.getEntity());
            String cleanedResponseContent = responseContent.replaceAll("\\n", "").replaceAll("\\\"", "\"");
            if(!cleanedResponseContent.contains("error"))
            {
                UserLookUpResponse userLookUpResponse=gson.fromJson(cleanedResponseContent,UserLookUpResponse.class);
                return new ResponseModel("Success",userLookUpResponse);
            }

            return new ResponseModel("Result",cleanedResponseContent); // Return response body as string
        }catch (IOException ex)
        {
            ex.printStackTrace();
            return new ResponseModel("Result","Không thể look up user trên firebase");
        }
    }
    public ResponseModel signInUser(UserDTO dto) throws IOException {
        // Your API key

        // Construct the URL
        String url = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + Constant.FIREBASE_API_KEY;

        // Create an instance of HttpClient
        HttpClient httpClient = HttpClients.createDefault();

        // Create an HTTP POST request
        HttpPost httpPost = new HttpPost(url);


        httpPost.setEntity(new StringEntity(gson.toJson(dto), "application/json", "UTF-8"));

        // Execute the request
        HttpResponse response = httpClient.execute(httpPost);

        // For example, you can use the EntityUtils to read the response content
        String responseContent = EntityUtils.toString(response.getEntity());
        String cleanedResponseContent = responseContent.replaceAll("\\n", "").replaceAll("\\\"", "\"");
        if(!cleanedResponseContent.contains("error"))
        {
            UserSignInResponse userSignInResponse=gson.fromJson(cleanedResponseContent,UserSignInResponse.class);
            return new ResponseModel("Success",userSignInResponse);
        }

        return new ResponseModel("Fail",cleanedResponseContent);
    }

    public ResponseModel signOutUser(String userId) {
        try {
            firebaseAuth.revokeRefreshTokens(userId);
            return new ResponseModel("Success",null);
        } catch (FirebaseAuthException e) {
            return new ResponseModel(e.getErrorCode().toString(),e.getMessage());
        }
    }


    public Account findById(String accountID)
    {
        Account account=accountRepository.findById(accountID).orElse(null);

        if(account==null)
        {
            return null;
        }
        account.setBirthday(DataConvert.parseBirthday(account.getBirthday()));
        return account;
    }

    public ResponseModel changePassword(String email, String newPassword)
    {
        try {
            //Check tài khoản
            UserRecord userRecord = firebaseAuth.getUserByEmail(email);
            //tao request thay doi mat khau
            UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(userRecord.getUid())
                    .setPassword(newPassword);
            //cap nhat tai khoan voi mat khau moi
            firebaseAuth.updateUser(request);
            return new ResponseModel(
                    "Success",
                    null
            );
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            return new ResponseModel(
                    "Fail",
                    null
            );
        }
    }

    public ResponseModel sendPasswordResetLink(String email)
    {
        try {
            // Check email của user
            UserRecord userRecord = firebaseAuth.getUserByEmail(email);
            if(userRecord!=null){
                // Khởi tạo URL rest mật khẩu
                String passwordResetLink = firebaseAuth.generatePasswordResetLink(email);
                return new ResponseModel(
                        passwordResetLink,
                        null
                );
            }
            return new ResponseModel(
                    "UserNotFound",
                    null
            );
        } catch (FirebaseAuthException e) {
            return new ResponseModel(
                    e.getMessage(),
                    null
            );
        }
    }
    public ResponseModel changeImage(String accountId, MultipartFile image){
        try {
            Account acc = findById(accountId);
            if(acc!=null){
                String imageUrl=imageService.uploadImage(image,"userImage/",accountId);
                acc.setImageUrl(imageUrl);
                System.out.println(imageUrl);
                accountRepository.save(acc);
                return new ResponseModel(
                        "Success",
                        acc
                );
            }
            return new ResponseModel(
                    "Fail",
                    null
            );
        }

        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public ResponseModel updateAccount(AccountDTO dto){
        Account acc=accountRepository.findById(dto.getAccountID()).orElse(null);
        if(acc!=null)
        {
            dto.setEmail(acc.getEmail());
            dto.setGender(acc.getGender());
            dto.setImageUrl(acc.getImageUrl());
//            dto.setBirthday(DataConvert.parseBirthday(dto.getBirthday()));
            acc=dto.toEntity();

            Account updatedAccount=accountRepository.save(acc);
            return new ResponseModel("Success",updatedAccount);
        }
        return new ResponseModel("AccountNotFound","Không tìm thấy tài khoản!");
    }
    private int calculatePoints(double orderValue)
    {
        //Chiết khấu 50%
        orderValue=orderValue*0.5;
        //Tạm tính 2 điểm mỗi 10000 chiết khấu
        return (int) (orderValue / 10000 * 2);
    }

    public ResponseModel updateAccountPoints(String accountId ,double orderTotal){
        Account acc=accountRepository.findById(accountId).orElse(null);
        if(acc!=null)
        {
            if(accountRepository.addPointsToAccount(acc.getAccountID(),calculatePoints(orderTotal))>0)
            {
                return new ResponseModel("Success","Đã cộng điểm");
            }
            return new ResponseModel("Fail","Có lỗi xảy ra");
        }
        return new ResponseModel("AccountNotFound","Không tìm thấy tài khoản!");
    }
}
