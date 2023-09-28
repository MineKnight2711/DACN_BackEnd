package com.example.dacn.entity;

import com.example.dacn.utils.DatetimeDeserialize;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewID;
    @Column(name = "score")
    private double score;
    @Column(name = "feedBack",length = 500)
    private String feedBack;
    @JsonProperty("birthday")
    @JsonDeserialize(using = DatetimeDeserialize.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "dateRecorded",length = 500)
    private Date dateRecorded;
    @ManyToOne
    @JoinColumn(name = "accountID")
    private Account account;
}
