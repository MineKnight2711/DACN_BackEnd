package com.example.dacn.modules.voucher_account.repository;

import com.example.dacn.entity.Account;
import com.example.dacn.entity.AccountVoucher;
import com.example.dacn.entity.ids.AccountVoucherId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountVoucherRepository extends JpaRepository<AccountVoucher, AccountVoucherId>
{
    @Query("SELECT COUNT(av) > 0 FROM AccountVoucher av WHERE av.account.accountID = :accountId AND av.voucher.voucherID = :voucherId")
    boolean findDuplicateAccountVoucher(String accountId,  String voucherId);
    @Query("SELECT av FROM AccountVoucher av WHERE av.account.accountID = :accountId")
    List<AccountVoucher> findAllByAccountId(String accountId);
}
