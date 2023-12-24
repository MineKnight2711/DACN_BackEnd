package com.example.dacn.modules.delivery.repository;

import com.example.dacn.entity.Delivery;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery,String>
{
    @Query(value = "SELECT @generated_id", nativeQuery = true)
    String findLatestAddressId();
    @Query("SELECT d FROM Delivery d WHERE d.account.accountID=:accountId")
    List<Delivery> findByAccountIdWithDetails(String accountId);

//    @Query("SELECT d FROM Delivery d LEFT JOIN FETCH d.orders o WHERE d.account.accountID = :accountId")
//    List<Delivery> findByAccountIdWithDetails(String accountId);
}
