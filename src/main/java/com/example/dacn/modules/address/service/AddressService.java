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
import java.util.Optional;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AccountService accountService;


    public ResponseModel createAddress(String accountID, AddressDTO dto) {
        dto.setAddressID("");

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
            Address getAddress= addressRepository.save(newAddress);
            String newAddressId=addressRepository.findLatestAddressId();
            getAddress.setAddressID(newAddressId);

            return new ResponseModel("Success", getAddress);
        } catch (Exception ex) {
            ex.printStackTrace();
            // Handle any exception and log the error
            System.out.println("An error occurred: " + ex.toString());
            return new ResponseModel("SomethingWrong", null);
        }
    }
    public ResponseModel updateAddress(String accountID,String addressID, AddressDTO dto) {
        try {
            Address add = addressRepository.findByAddressIdAndAccountId(addressID,accountID);
            if(add==null)
            {
                return new ResponseModel("AddressNotFound", null);
            }
            Account acc = accountService.findById(accountID);
            if (acc == null) {
                return new ResponseModel("AccountNotFound", null);
            }
            // Convert DTO to entity and set the account
            add = dto.toEntity();
            add.setAddressID(addressID);
            add.setAccount(acc);
            if(dto.isDefaultAddress())
            {
                Address updatedAddress= addressRepository.save(add);

                Address defaultAddressButUnlikeUpdatedOne=addressRepository.findDefaultAddressNotEqualsAddressId(accountID,addressID);

                if(defaultAddressButUnlikeUpdatedOne!=null)
                {
                    defaultAddressButUnlikeUpdatedOne.setDefaultAddress(false);
                    addressRepository.save(defaultAddressButUnlikeUpdatedOne);
                    return new ResponseModel("Success", updatedAddress);
                }

                return new ResponseModel("Success", updatedAddress);
            }
            else
            {
                Address defaultAddressButUnlikeUpdatedOne=addressRepository.findDefaultAddressNotEqualsAddressId(accountID,addressID);
                if(defaultAddressButUnlikeUpdatedOne==null)
                {
                    return new ResponseModel("Fail", "Bạn phải có ít nhất 1 địa chỉ mặc định!");
                }
                Address updatedAddress= addressRepository.save(add);
                return new ResponseModel("Success", updatedAddress);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            // Handle any exception and log the error
            System.out.println("An error occurred: " + ex.toString());
            return new ResponseModel("SomethingWrong", null);
        }
    }
    public ResponseModel getAllAddresses() {
        List<Address> addresses = addressRepository.findAll();
        if (!addresses.isEmpty())
        {
            // Sắp xếp các địa chỉ sao cho defaultAddress là true được đưa lên đầu danh sách
            Collections.sort(addresses, (a1, a2) -> {
                if (a1.isDefaultAddress() == a2.isDefaultAddress())
                {
                    return 0; // Giữ nguyên vị trí nếu defaultAddress giống nhau
                }
                return a1.isDefaultAddress() ? -1 : 1; // Đưa địa chỉ với defaultAddress là true lên đầu
            });
            return new ResponseModel("Success", addresses);
        }
        return new ResponseModel("NoAddress", null);
    }
    public ResponseModel deleteAddress(String addressID) {
        Optional<Address> optionalAddress = addressRepository.findById(addressID);
        if (optionalAddress.isPresent())
        {
            Address address = optionalAddress.get();
            if (address.isDefaultAddress())
            {
                return new ResponseModel("IsDefaultAddress", null);
            }
            addressRepository.delete(address);
            return new ResponseModel("Address deleted successfully", null);
        }
        else
        {
            return new ResponseModel("Address not found", null);
        }
    }
}


