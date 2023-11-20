package com.example.dacn.modules.voucher_account.service;

import com.example.dacn.entity.AccountVoucher;
import com.example.dacn.modules.voucher_account.repository.AccountVoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountVoucherService {
    @Autowired
    private AccountVoucherRepository accountVoucherRepository;
    public boolean createNewAccountVoucher(AccountVoucher accountVoucher){
        try
        {

            accountVoucherRepository.save(accountVoucher);
            return true;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
    }

}
