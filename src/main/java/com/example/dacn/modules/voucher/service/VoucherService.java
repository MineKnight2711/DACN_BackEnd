package com.example.dacn.modules.voucher.service;

import com.example.dacn.entity.Account;
import com.example.dacn.entity.ResponseModel;
import com.example.dacn.entity.Voucher;
import com.example.dacn.modules.account.service.AccountService;

import com.example.dacn.modules.voucher.dto.VoucherDTO;
import com.example.dacn.modules.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VoucherService {
    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private AccountService accountService;

    public ResponseModel createVoucher(String accountID, VoucherDTO dto) {
        try {
            Account acc = accountService.findById(accountID);
            if (acc == null) {
                return new ResponseModel("AccountNotFound", null);
            }

            Voucher newVoucher = dto.toEntity();
            newVoucher.setAccount(acc);

            voucherRepository.save(newVoucher);

            return new ResponseModel("Success", newVoucher);
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return new ResponseModel("SomethingWrong", null);
        }
    }

    public ResponseModel getAllVouchersSortedByDiscount() {

        return new ResponseModel("SortList", voucherRepository.findAllByOrderByDiscountDesc());
    }
//    private List<VoucherDTO> convertToDTOs(List<Voucher> vouchers) {
//        List<VoucherDTO> voucherDTOs = new ArrayList<>();
//        for (Voucher voucher : vouchers) {
//            VoucherDTO voucherDTO = new VoucherDTO();
//            voucherDTO.setVoucherID(voucher.getVoucherID());
//            voucherDTO.setStartDate(voucher.getStartDate());
//            voucherDTO.setExpDate(voucher.getExpDate());
//            voucherDTO.setVoucherName(voucher.getVoucherName());
//            voucherDTO.setDiscount(voucher.getDiscount());
//            voucherDTOs.add(voucherDTO);
//        }
//        return voucherDTOs;
//    }
}