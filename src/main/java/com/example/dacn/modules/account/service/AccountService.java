package com.example.dacn.modules.account.service;

import com.example.dacn.entity.Account;
import com.example.dacn.modules.account.repository.AccountRepository;
import com.example.dacn.utils.DataConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;


    public Account createAccount(Account account)
    {
        DataConvert dataConvert=new DataConvert();
        if(account.getBirthday()!=null)
        {
            account.setBirthday(dataConvert.parseBirthday(account.getBirthday()));
        }
        else
        {
            account.setBirthday(null);
        }
        accountRepository.save(account);
        return account;
    }

}
