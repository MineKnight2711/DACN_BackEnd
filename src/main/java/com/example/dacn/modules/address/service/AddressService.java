package com.example.dacn.modules.address.service;

import com.example.dacn.entity.Account;
import com.example.dacn.entity.Address;
import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.account.service.AccountService;
import com.example.dacn.modules.address.dto.AddressDTO;
import com.example.dacn.modules.address.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

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
    public ResponseModel getAllAddresses() {
        List<Address> addresses = addressRepository.findAll();
        if (!addresses.isEmpty()) {
            // Sắp xếp các địa chỉ sao cho defaultAddress là true được đưa lên đầu danh sách
            Collections.sort(addresses, (a1, a2) -> {
                if (a1.isDefaultAddress() == a2.isDefaultAddress()) {
                    return 0; // Giữ nguyên vị trí nếu defaultAddress giống nhau
                }
                return a1.isDefaultAddress() ? -1 : 1; // Đưa địa chỉ với defaultAddress là true lên đầu
            });
            return new ResponseModel("Success", addresses);
        }
        return new ResponseModel("NoAddress", null);
    }
}

