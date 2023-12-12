package com.example.dacn.modules.voucher.service;

import com.example.dacn.entity.Account;
import com.example.dacn.entity.AccountVoucher;
import com.example.dacn.entity.ResponseModel;
import com.example.dacn.entity.Voucher;
import com.example.dacn.entity.ids.AccountVoucherId;
import com.example.dacn.modules.account.service.AccountService;

import com.example.dacn.modules.voucher.dto.VoucherDTO;
import com.example.dacn.modules.voucher.repository.VoucherRepository;
import com.example.dacn.modules.voucher_account.service.AccountVoucherService;
import com.example.dacn.utils.DataConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VoucherService {
    @Autowired
    private VoucherRepository voucherRepository;

    public ResponseModel createVoucher(VoucherDTO dto) {
        dto.setVoucherID("");
        try {

            Voucher newVoucher = dto.toEntity();
            newVoucher.setStartDate(newVoucher.getStartDate());
            newVoucher.setExpDate(newVoucher.getExpDate());
            voucherRepository.save(newVoucher);
            return new ResponseModel("Success", newVoucher);
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return new ResponseModel("SomethingWrong", null);
        }
    }

    public ResponseModel getAllVouchersSortedByDiscount() {
        return new ResponseModel("Success", voucherRepository.findAllByOrderByPointsRequiredDesc());
    }

    public Voucher findById(String voucherId)
    {
        return voucherRepository.findById(voucherId).orElse(null);
    }

    public ResponseModel deleteVoucher(String voucherId)
    {
        Voucher voucher=voucherRepository.findById(voucherId).orElse(null);
        if(voucher != null)
        {
            voucherRepository.delete(voucher);
            return  new ResponseModel("Success","Xoá voucher thành công");
        }
        return  new ResponseModel("Fail","Có lỗi xảy ra");
    }

    public ResponseModel updateVoucher(VoucherDTO dto)
    {
        Voucher voucher=voucherRepository.findById(dto.getVoucherID()).orElse(null);
        if(voucher!=null)
        {
            voucher=dto.toEntity();
            voucher.setVoucherID(dto.getVoucherID());
            voucherRepository.save(voucher);
            return  new ResponseModel("Success","Cập nhật voucher thành công");
        }
        return  new ResponseModel("Fail","Có lỗi xảy ra");
    }
}