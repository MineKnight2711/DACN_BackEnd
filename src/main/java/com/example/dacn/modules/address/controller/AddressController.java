package com.example.dacn.modules.address.controller;

import com.example.dacn.entity.Address;
import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.address.dto.AddressDTO;
import com.example.dacn.modules.address.service.AddressService;
import com.example.dacn.modules.review.dto.ReviewDTO;
import com.example.dacn.modules.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/address")
public class AddressController {
    @Autowired
    private AddressService addressService;
    @PostMapping("/{accountID}")
    public ResponseModel createAddress(@PathVariable("accountID") String accountID, @ModelAttribute AddressDTO dto)
    {
        return addressService.createAddress(accountID,dto);
    }
    @GetMapping
    public ResponseModel getAllAddresses()
    {
        return addressService.getAllAddresses();
    }

    @DeleteMapping("/{addressID}")
    public ResponseModel deleteAddress(@PathVariable String addressID)
    {
        return addressService.deleteAddress(addressID);
    }
}
