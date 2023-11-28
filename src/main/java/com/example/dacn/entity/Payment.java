package com.example.dacn.entity;

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
@Entity(name="Payment")
@Table(name="Payment")
public class Payment {
    @Id
    private String paymentID;

    @Column(name = "payment_method")
    private String paymentMethod;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL)
    @JsonIgnore
    @JsonManagedReference
    private List<PaymentDetails> paymentDetails;

}
