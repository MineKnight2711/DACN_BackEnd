package com.example.dacn.modules.address.dto;

import com.example.dacn.entity.Address;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AddressDTO {

    private String addressID;
    private String ward;
    private String district;
    private String province;
    private String details;
    private String addressName;
    private String receiverName;
    private String receiverPhone;
    private boolean defaultAddress;

    public Address toEntity(){
        Address address = new Address();
        address.setAddressID(this.addressID);
        address.setWard(this.ward);
        address.setDistrict(this.district);
        address.setProvince(this.province);
        address.setDetails(this.details);
        address.setAddressName(this.addressName);
        address.setReceiverName(this.receiverName);
        address.setReceiverPhone(this.receiverPhone);
        address.setDefaultAddress(this.defaultAddress);
        return address;
    }
}
