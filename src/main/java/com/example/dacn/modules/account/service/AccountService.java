package com.example.dacn.modules.account.service;
import com.example.dacn.entity.Account;
import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.account.dto.AccountDTO;

import com.example.dacn.modules.account.repository.AccountRepository;
import com.example.dacn.utils.DataConvert;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;


@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private DataConvert dataConvert;
    @Autowired
    private FirebaseAuth firebaseAuth;



    public ResponseModel getAccountById(String id)
    {
        Optional<Account> account=accountRepository.findById(id);
        if(account.isEmpty())
        {
            return new ResponseModel("NoAccount",null);
        }
        return new ResponseModel("Success",account.get());

    }
    public ResponseModel getAccountByEmail(String email)
    {
       Account account=accountRepository.findByEmail(email);
        if(account==null)
        {
            return new ResponseModel("NoAccount",null);
        }
        return new ResponseModel("Success",account);

    }
    public ResponseModel  createAccount(AccountDTO dto)
    {
        dto.setAccountID("");
        if(dto.getBirthday()!=null)
        {
            dto.setBirthday(dataConvert.parseBirthday(dto.getBirthday()));
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
    public ResponseModel signInUser(String requestLoginJson) throws IOException {
        // Your API key
        String apiKey = "AIzaSyA-_uiCdzqybddZNo5sB-oJcwPXhDmrUHs";

        // Construct the URL
        String url = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + apiKey;

        // Create an instance of HttpClient
        HttpClient httpClient = HttpClients.createDefault();

        // Create an HTTP POST request
        HttpPost httpPost = new HttpPost(url);


        httpPost.setEntity(new StringEntity(requestLoginJson, "application/json", "UTF-8"));

        // Execute the request
        HttpResponse response = httpClient.execute(httpPost);

        // Handle the response here and convert it into your ResponseModel
        // You may need to read the response content and parse it into the desired format

        // For example, you can use the EntityUtils to read the response content
        String responseContent = EntityUtils.toString(response.getEntity());

        // Process the responseContent and create your ResponseModel object
//        ResponseModel responseModel = processResponse(responseContent);

        return new ResponseModel("SignInTest",responseContent);
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
        Optional<Account> account=accountRepository.findById(accountID);

        if(account.isEmpty())
        {
            return null;
        }
        return account.get();
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
    public ResponseModel changeImage(String accountId,String newUrl){
        Account acc = findById(accountId);
        if(acc!=null){
            acc.setImageUrl(newUrl);
            System.out.println(newUrl);
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

}
