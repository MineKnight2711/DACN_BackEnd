package com.example.dacn.modules.address.repository;

import com.example.dacn.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address,String>
{
    @Query(value = "SELECT @generated_id", nativeQuery = true)
    String findLatestAddressId();
    @Query("SELECT a FROM Address a WHERE a.addressID = :addressId AND a.account.accountID = :accountId")
    Address findByAddressIdAndAccountId(String addressId,String accountId);

    @Query("SELECT a FROM Address a WHERE a.defaultAddress = true AND a.account.accountID = :accountId AND a.addressID != :addressId")
    Address findDefaultAddressNotEqualsAddressId(String accountId,String addressId);

    @Query("SELECT a FROM Address a WHERE a.account.accountID = :accountId")
    List<Address> findAllByAccountId(String accountId);
}
