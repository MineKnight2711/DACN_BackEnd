package com.example.dacn.modules.account.repository;

import com.example.dacn.entity.Account;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, String>
{
    @Query(value = "SELECT @generated_id", nativeQuery = true)
    String findLatestAccountId();
    @Query("SELECT a FROM Account a WHERE a.email = :email")
    Account findByEmail(@Param("email") String email);
    @Query("SELECT a FROM Account a WHERE a.role != :role")
    List<Account> getAllStaff(String role);
    @Transactional
    @Modifying
    @Query("UPDATE Account a SET " +
            "a.points = COALESCE(a.points, 0) + :points, " +
            "a.lifetimePoints = COALESCE(a.lifetimePoints, 0) + :points " +
            "WHERE a.accountID = :accountId")
    int addPointsToAccount( String accountId, int points);
    @Query("SELECT a FROM Account a WHERE a.role = :role")
    List<Account> getAllDeliver(String role);
}
