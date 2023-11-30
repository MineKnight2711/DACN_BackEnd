package com.example.dacn.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@Data
@Getter
@Setter
@Entity(name = "Review")
@Table(name = "Review")
public class Review {
    @Id
    private String reviewID;
    @Column(name = "score")
    private double score;
    @Column(name = "feedBack",length = 500)
    private String feedBack;
    @JsonProperty("birthday")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "dateRecorded",length = 500)
    private Date dateRecorded;
    @ManyToOne
    @JoinColumn(name = "accountID",nullable = false)
    private Account account;
    //Quan hệ 1 nhiều tới bảng order
    @ManyToOne
    @JoinColumn(name = "orderID", nullable = false)
    private Orders order_review;
}
