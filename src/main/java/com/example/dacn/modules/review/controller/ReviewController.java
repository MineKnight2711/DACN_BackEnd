package com.example.dacn.modules.review.controller;

import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.review.dto.ReviewDTO;
import com.example.dacn.modules.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
public class ReviewController
{
    @Autowired
    private ReviewService reviewService;
    @PostMapping("/{accountID}")
    public ResponseModel createReview(@PathVariable("accountID") String accountID, @ModelAttribute ReviewDTO dto)
    {
        return reviewService.createReview(accountID,dto);
    }
}
