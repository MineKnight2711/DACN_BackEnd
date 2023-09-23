package com.example.dacn.modules.account.service;

import com.example.dacn.entity.Account;
import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.account.dto.AccountDTO;
import com.example.dacn.modules.account.repository.AccountRepository;
import com.example.dacn.utils.DataConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public ResponseModel createAccount(AccountDTO dto)
    {

        DataConvert dataConvert=new DataConvert();
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
            Account result= accountRepository.save(dto.toEntity());
            return new ResponseModel("Success",result);
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
            return new ResponseModel("SomethingWrong",null);
        }

    }

    private boolean validatePassword(String passwordHashed,String passwordInput)
    {
        return BCrypt.checkpw(passwordInput,passwordHashed);
    }

    public ResponseModel login(String email,String password)
    {
        String hashedPassword= accountRepository.getPasswordByEmail(email);
        if(hashedPassword==null)
        {
            return new ResponseModel("AccountNotFound",null);
        }
        if(validatePassword(hashedPassword,password))
        {
            return new ResponseModel("Success",null);
        }
        return new ResponseModel("Fail",null);
    }

}
