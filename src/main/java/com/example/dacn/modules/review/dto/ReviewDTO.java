package com.example.dacn.modules.review.dto;

import com.example.dacn.entity.Review;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Getter
@Setter
public class ReviewDTO
{
    private Long reviewID;
    private double score;
    private String feedBack;
    @JsonProperty("birthday")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateRecorded;

    public Review toEntity(){
        Review review = new Review();
        review.setScore(this.score);
        review.setFeedBack(this.feedBack);
        review.setDateRecorded(this.dateRecorded);
        return review;
    }
}
