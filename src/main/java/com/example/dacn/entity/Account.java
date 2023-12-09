package com.example.dacn.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
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
    private String accountID;
    @Column(name = "fullName",length = 200)
    private String fullName;
    @Column(name = "email",length = 200)
    private String email;
    @Column(name = "role")
    private String role;
    @Temporal(TemporalType.TIMESTAMP)
    @Past(message = "NgaySinh phai nho hon ngay hien tai")
    @JsonProperty("birthday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone="Asia/Ho_Chi_Minh")
    @Column(name = "birthday")
    private Date birthday;
    @Column(name = "phoneNumber")
    private String phoneNumber;
    @Column(name = "gender")
    private String gender;
    @Column(name = "imageUrl")
    private String imageUrl;
    @Column(name = "points", columnDefinition = "integer default 0")
    private Integer points;
    @Column(name = "lifetimePoints", columnDefinition = "integer default 0")
    private Integer lifetimePoints;
    @Column(name = "tier", columnDefinition = "varchar(255) default 'Bronze'")
    private String tier;
    @OneToMany (mappedBy = "account", cascade = CascadeType.ALL)
    @JsonIgnore
    @JsonManagedReference
    private List<Address> addresses;
    @ManyToMany
    @JoinTable(name = "AccountVoucher",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "voucher_id"))
    @JsonIgnore
//    @JsonManagedReference
    private List<Voucher> vouchers;


    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonIgnore
    @JsonManagedReference
    private List<Cart> carts;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonIgnore
    @JsonManagedReference
    private List<Favorite> favorites;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonIgnore
    @JsonManagedReference
    private List<Orders> orders;
}
