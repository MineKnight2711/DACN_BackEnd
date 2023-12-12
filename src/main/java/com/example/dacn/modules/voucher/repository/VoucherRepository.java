package com.example.dacn.modules.voucher.repository;


import com.example.dacn.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher,String> {
    @Query(value = "SELECT @generated_id", nativeQuery = true)
    String findLatestVoucherId();
    @Query("SELECT v FROM Voucher v ORDER BY v.pointsRequired DESC")
    List<Voucher> findAllByOrderByPointsRequiredDesc();
}

