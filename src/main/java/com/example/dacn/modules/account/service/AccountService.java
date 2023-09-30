package com.example.dacn.modules.account.service;
import com.example.dacn.entity.Account;
import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.account.dto.AccountDTO;
import com.example.dacn.modules.account.dto.ChangePassDTO;
import com.example.dacn.modules.account.repository.AccountRepository;
import com.example.dacn.utils.DataConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import java.util.Optional;

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
    public ResponseModel changePassword(ChangePassDTO changePassDTO)
    {
        String passwordHashed=accountRepository.getPasswordByEmail(changePassDTO.getEmail());
        if(passwordHashed==null||passwordHashed=="")
        {
            return new ResponseModel("AccountNotFound",null);
        }
        if(BCrypt.checkpw(changePassDTO.getOldPassword(),passwordHashed))
        {
            Account acc=accountRepository.findByEmail(changePassDTO.getEmail());
            String salt = BCrypt.gensalt();
            String newHashedPassword= BCrypt.hashpw(changePassDTO.getNewPassword(), salt);
            acc.setPassword(newHashedPassword);
            accountRepository.save(acc);
            return new ResponseModel("Success",null);
        }
        else
        {
            return new ResponseModel("WrongPass",null);
        }
    }
    public ResponseModel login(String email,String password)
    {
        Account account= accountRepository.findByEmail(email);
        if(account==null)
        {
            return new ResponseModel("AccountNotFound",null);
        }
        if(validatePassword(account.getPassword(),password))
        {
            return new ResponseModel("Success",account);
        }
        return new ResponseModel("Fail",null);
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
}
