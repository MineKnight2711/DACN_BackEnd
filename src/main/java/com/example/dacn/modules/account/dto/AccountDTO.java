package com.example.dacn.modules.account.dto;

import com.example.dacn.entity.Account;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.validation.constraints.Past;
import java.util.Date;
@Getter
@Setter
public class AccountDTO
{
    private String accountID;
    private String password;
    private String fullName;
    private String email;
    @Temporal(TemporalType.TIMESTAMP)
    @Past(message = "NgaySinh phai nho hon ngay hien tai")
    @JsonProperty("birthday")

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String gender;
    private String phoneNumber;
    private String imageUrl;
    private String role;
    private Integer points;
    private Integer lifetimePoints;
    private String tier;

    public Account toEntity()
    {
        Account account = new Account();
        account.setAccountID(this.accountID);
        account.setFullName(this.fullName);
        account.setEmail(this.email);
        account.setBirthday(this.birthday);
        account.setGender(this.gender);
        account.setPhoneNumber(this.phoneNumber);
        account.setImageUrl(this.imageUrl);
        account.setRole(this.role);
        account.setPoints(this.points);
        account.setLifetimePoints(this.lifetimePoints);
        account.setTier(this.tier);
        return account;
    }
}
