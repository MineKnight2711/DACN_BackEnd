package com.example.dacn.entity;

import com.example.dacn.utils.DataConvert;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@Entity(name = "Orders")
@Table(name = "Orders")
public class Orders {
    @Id
    private String orderID;

    @Column(name = "status")
    private String status;
    @JsonProperty("orderDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "orderDate")
    private Date orderDate;
    @Column(name = "deliveryInfo")
    private String deliveryInfo;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "score")
    private Double score;
    @Column(name = "feedBack")
    private String feedBack;
    @Column(name = "dateFeedBack")
    private Date dateFeedBack;
    // quan he nhieu nhieu toi bang dish
    @ManyToMany
    @JoinTable(name = "OrderDetail",
            joinColumns = @JoinColumn(name = "dish_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    @JsonIgnore
    private List<Dish> dishes;

//    Quan hệ 1 nhiều tới bảng voucher
    @ManyToOne
    @JoinColumn(name = "voucherID")
    private Voucher voucher;
    @ManyToOne
    @JoinColumn(name = "accountID",nullable = false)
    private Account account;


    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    @JsonIgnore
    @JsonManagedReference
    private List<PaymentDetails> paymentDetails;


}
