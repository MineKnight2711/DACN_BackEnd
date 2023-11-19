package com.example.dacn.modules.voucher_account.repository;

import com.example.dacn.entity.AccountVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountVoucherRepository extends JpaRepository<AccountVoucher, AccountVoucher.AccountVoucherId>
{

}
