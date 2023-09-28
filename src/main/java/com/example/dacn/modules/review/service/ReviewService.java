package com.example.dacn.modules.review.service;

import com.example.dacn.entity.Account;
import com.example.dacn.entity.ResponseModel;
import com.example.dacn.entity.Review;
import com.example.dacn.modules.account.service.AccountService;
import com.example.dacn.modules.review.dto.ReviewDTO;
import com.example.dacn.modules.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService
{
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private AccountService accountService;
    public ResponseModel createReview(String accountID,ReviewDTO dto)
    {
        try
        {
            Account acc=accountService.findById(accountID);
            if(acc==null)
            {
                return new ResponseModel("AccountNotFound",null);
            }
            Review newReview= dto.toEntity();
            newReview.setAccount(acc);
            reviewRepository.save(newReview);
            return new ResponseModel("Success",newReview);
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
            return new ResponseModel("SomeThingWrong",null);
        }
    }
}
