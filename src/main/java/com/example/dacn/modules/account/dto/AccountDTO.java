package com.example.dacn.modules.account.dto;

import com.example.dacn.entity.Account;
import com.example.dacn.utils.DatetimeDeserialize;
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
    @JsonDeserialize(using = DatetimeDeserialize.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String gender;
    private String address;
    private String imageUrl;

    public AccountDTO() {}

    public AccountDTO(String accountID, String password, String fullName, String email, Date birthday, String gender, String address, String imageUrl) {
        this.accountID = accountID;
        this.fullName = fullName;
        this.password=password;
        this.email = email;
        this.birthday = birthday;
        this.gender = gender;
        this.address = address;
        this.imageUrl = imageUrl;
    }
    private String encryptPassword(String passWord)
    {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(passWord, salt);
    }

    public Account toEntity()
    {
        Account account = new Account();
        account.setAccountID(this.accountID);
        account.setFullName(this.fullName);
        account.setEmail(this.email);
        account.setBirthday(this.birthday);
        account.setGender(this.gender);
        account.setAddress(this.address);
        account.setImageUrl(this.imageUrl);
        account.setPassword(encryptPassword(this.password));
        return account;
    }
}
