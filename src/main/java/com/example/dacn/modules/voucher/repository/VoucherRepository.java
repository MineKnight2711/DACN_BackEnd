package com.example.dacn.modules.voucher.repository;


import com.example.dacn.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher,Long> {
    @Query("SELECT v FROM Voucher v ORDER BY v.discount DESC")
    List<Voucher> findAllByOrderByDiscountDesc();
}

