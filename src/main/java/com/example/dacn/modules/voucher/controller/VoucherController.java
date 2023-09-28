package com.example.dacn.modules.voucher.controller;

import com.example.dacn.entity.ResponseModel;

import com.example.dacn.entity.Voucher;
import com.example.dacn.modules.voucher.dto.VoucherDTO;
import com.example.dacn.modules.voucher.repository.VoucherRepository;
import com.example.dacn.modules.voucher.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Service
@RequestMapping("/api/voucher")
public class VoucherController
{
    @Autowired
    private VoucherService voucherService;

    @Autowired
    private VoucherRepository voucherRepository;
    @PostMapping("/{accountID}")
    public ResponseModel createVoucher(@PathVariable("accountID") String accountID, @ModelAttribute VoucherDTO dto) {
        return voucherService.createVoucher(accountID, dto);
    }

    @GetMapping("/all")
    public ResponseModel getAllVouchersSortedByDiscount() {
        return voucherService.getAllVouchersSortedByDiscount();
    }

}
