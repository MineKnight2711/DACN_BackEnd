package com.example.dacn.modules.address.controller;

import com.example.dacn.entity.Address;
import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.address.dto.AddressDTO;
import com.example.dacn.modules.address.service.AddressService;
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
    @GetMapping("/{accountId}")
    public ResponseModel getAllAddresses(@PathVariable("accountId") String accountId)
    {
        return addressService.getAllAddresses(accountId);
    }
    @PutMapping("/{addressId}")
    public ResponseModel updateAddress(@PathVariable("addressId") String addressId,
                                       @RequestParam("accountID") String accountID,
                                       @ModelAttribute AddressDTO dto)
    {
        return addressService.updateAddress(accountID,addressId,dto);
    }
    @DeleteMapping("/{addressID}")
    public ResponseModel deleteAddress(@PathVariable String addressID)
    {
        return addressService.deleteAddress(addressID);
    }
}
