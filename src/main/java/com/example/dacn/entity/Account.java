package com.example.dacn.entity;

import com.example.dacn.utils.DatetimeDeserialize;
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
    @JsonDeserialize(using = DatetimeDeserialize.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birthday")
    private Date birthday;
    @Column(name = "phoneNumber")
    private String phoneNumber;
    @Column(name = "gender")
    private String gender;
    @Column(name = "imageUrl")
    private String imageUrl;
    @OneToMany (mappedBy = "account", cascade = CascadeType.ALL)
    @JsonIgnore
    @JsonManagedReference
    private List<Address> addresses;
    @ManyToMany
    @JoinTable(name = "account_voucher",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "voucher_id"))
    private List<Voucher> vouchers;

    @OneToMany (mappedBy = "account", cascade = CascadeType.ALL)
    @JsonIgnore
    @JsonManagedReference
    private List<Review> reviews;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonIgnore
    @JsonManagedReference
    private List<Cart> carts;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonIgnore
    @JsonManagedReference
    private List<Orders> orders;
}
