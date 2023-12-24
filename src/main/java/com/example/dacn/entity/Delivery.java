package com.example.dacn.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@Entity(name="Delivery")
@Table(name="Delivery")
public class Delivery
{
    @Id
    private String deliveryId;
    @Column(name = "dateAccepted")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone="Asia/Ho_Chi_Minh")
    private Date dateAccepted;
    @Column(name = "dateDelivered")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone="Asia/Ho_Chi_Minh")
    private Date dateDelivered;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "accountID")
    private Account account;

    @ManyToMany( cascade = CascadeType.ALL)
    @JoinTable(name = "DeliveryDetails",
            joinColumns = @JoinColumn(name = "delivery_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    @JsonIgnore
    private List<Orders> orders;
}
