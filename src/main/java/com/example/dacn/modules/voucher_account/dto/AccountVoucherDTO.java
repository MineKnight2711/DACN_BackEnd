package com.example.dacn.modules.voucher_account.dto;

import com.example.dacn.entity.AccountVoucher;
import com.example.dacn.entity.ids.AccountVoucherId;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AccountVoucherDTO
{
    private String accountId;
    private String voucherId;
    public AccountVoucher toEntity()
    {
        AccountVoucher accountVoucher=new AccountVoucher();
        AccountVoucherId accountVoucherId=new AccountVoucherId();
        accountVoucherId.setAccount_id(this.accountId);
        accountVoucherId.setVoucher_id(this.voucherId);
        accountVoucher.setAccountVoucherId(accountVoucherId);
        return accountVoucher;
    }
}
