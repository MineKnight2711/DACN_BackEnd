package com.example.dacn.modules.account.repository;

import com.example.dacn.entity.Account;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String>
{
    @Query("SELECT a FROM Account a WHERE a.email = :email")
    Account findByEmail(@Param("email") String email);
    @Transactional
    @Modifying
    @Query("UPDATE Account a SET a.points = a.points + :points, a.lifetimePoints = a.lifetimePoints + :points WHERE a.accountID = :accountId")
    int addPointsToAccount( String accountId, int points);

}
