package com.example.dacn.modules.voucher_account.service;

import com.example.dacn.entity.Account;
import com.example.dacn.entity.AccountVoucher;
import com.example.dacn.entity.ResponseModel;
import com.example.dacn.entity.Voucher;
import com.example.dacn.modules.account.service.AccountService;
import com.example.dacn.modules.voucher.service.VoucherService;
import com.example.dacn.modules.voucher_account.dto.AccountVoucherDTO;
import com.example.dacn.modules.voucher_account.repository.AccountVoucherRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountVoucherService {
    @Autowired
    private AccountVoucherRepository accountVoucherRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private VoucherService voucherService;
    public ResponseModel getAccountVoucherByAccountId(String accountId)
    {
        List<AccountVoucher> accountVouchers= accountVoucherRepository.findAllByAccountId(accountId);

        if(accountVouchers.isEmpty())
        {
            return new ResponseModel("NoVoucher","Tài khoản không có voucher nào");
        }
        return new ResponseModel("Success",accountVouchers);
    }
    public ResponseModel createAccountVoucher(AccountVoucherDTO dto)
    {
        try
        {
            AccountVoucher accountVoucher=dto.toEntity();
            Account account=accountService.findById(dto.getAccountId());
            if(account==null)
            {
                return new ResponseModel("AccountNotFound","Không tìm thấy tài khoản!");
            }
            Voucher voucher=voucherService.findById(dto.getVoucherId());
            if(voucher==null)
            {
                return new ResponseModel("VoucherNotFound","Không tìm thấy voucher!");
            }
            if(accountVoucherRepository.findDuplicateAccountVoucher(dto.getAccountId(), dto.getVoucherId()))
            {
                return new ResponseModel("DuplicatedVoucher","Voucher bị trùng vui lòng tăng số lượng!");
            }
            if(account.getPoints()<voucher.getPointsRequired())
            {
                return new ResponseModel("NotEnoughPoints","Bạn chưa đủ điểm để nhận voucher này!");
            }
            account.setPoints(account.getPoints()-voucher.getPointsRequired());
            accountVoucher.setAccount(account);
            accountVoucher.setVoucher(voucher);
            AccountVoucher savedAccountVoucher= accountVoucherRepository.save(accountVoucher);
            return new ResponseModel("Success",savedAccountVoucher);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return new ResponseModel("Fail","Lỗi chưa xác định!");
        }
    }
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
