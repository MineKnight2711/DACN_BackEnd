package com.example.dacn.entity;

import com.example.dacn.utils.DatetimeDeserialize;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@Entity(name="Account")
@Table(name="Account")
public class Account {
    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private String accountID;
    @Column(name = "fullName",length = 200)
    private String fullName;
    @Column(name = "password",length = 500)
    private String password;
    @Column(name = "email",length = 200)
    private String email;
    @Temporal(TemporalType.TIMESTAMP)
    @Past(message = "NgaySinh phai nho hon ngay hien tai")
    @JsonProperty("birthday")
    @JsonDeserialize(using = DatetimeDeserialize.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birthday")
    private Date birthday;
    @Column(name = "gender")
    private String gender;
    @Column(name = "address")
    private String address;
    @Column(name = "imageUrl")
    private String imageUrl;
    @OneToMany (mappedBy = "account", cascade = CascadeType.ALL)
    private List<Address> addresses;
//    @Column(name = "isFingerPrintAuthenticated")
//    private boolean isFingerPrintAuthenticated;

    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL)
    @JsonIgnore
    @JsonManagedReference
    private List<VoucherAccount> voucherAccounts;

    @OneToMany
    @JsonIgnore
    @JsonManagedReference
    private List<Cart> carts;
}
