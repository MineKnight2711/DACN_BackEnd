package com.example.dacn.modules.address.dto;

import com.example.dacn.entity.Account;
import com.example.dacn.entity.Address;
import com.example.dacn.entity.Review;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AddressDTO {

    private String addressID;
    private String address;
    private String receiverName;
    private String receiverPhone;
    private boolean defaultAddress;

    public Address toEntity(){
        Address address = new Address();
        address.setAddress(this.address);
        address.setReceiverName(this.receiverName);
        address.setReceiverPhone(this.receiverPhone);
        address.setDefaultAddress(this.defaultAddress);
        return address;
    }
}
