package com.example.dacn.modules.account.service;
import com.example.dacn.entity.Account;
import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.account.dto.AccountDTO;
import com.example.dacn.modules.account.dto.ChangePassDTO;
import com.example.dacn.modules.account.repository.AccountRepository;
import com.example.dacn.utils.DataConvert;
import com.google.cloud.firestore.DocumentReference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

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
                    newUrl
            );
        }
        return new ResponseModel(
                "Fail",
                null
        );

    }
}
