package com.example.dacn.modules.address.service;

import com.example.dacn.entity.Account;
import com.example.dacn.entity.Address;
import com.example.dacn.entity.ResponseModel;
import com.example.dacn.entity.Review;
import com.example.dacn.modules.account.service.AccountService;
import com.example.dacn.modules.address.dto.AddressDTO;
import com.example.dacn.modules.address.repository.AddressRepository;
import com.example.dacn.modules.review.dto.ReviewDTO;
import com.example.dacn.modules.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AccountService accountService;


    public ResponseModel createAddress(String accountID, AddressDTO dto) {
        try {
            // Try to retrieve the account
            Account acc = accountService.findById(accountID);
            if (acc == null) {
                return new ResponseModel("AccountNotFound", null);
            }

            // Convert DTO to entity and set the account
            Address newAddress = dto.toEntity();
            newAddress.setAccount(acc);

            // Save the address
            addressRepository.save(newAddress);

            return new ResponseModel("Success", newAddress);
        } catch (Exception ex) {
            // Handle any exception and log the error
            System.out.println("An error occurred: " + ex.toString());
            return new ResponseModel("SomethingWrong", null);
        }
    }
}

