package com.example.dacn.modules.voucher.controller;

import com.example.dacn.entity.ResponseModel;

import com.example.dacn.modules.voucher.dto.VoucherDTO;
import com.example.dacn.modules.voucher.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@Service
@RequestMapping("/api/voucher")
public class VoucherController
{
    @Autowired
    private VoucherService voucherService;
    @PostMapping
    public ResponseModel createVoucher(@RequestBody VoucherDTO dto) {
        return voucherService.createVoucher(dto);
    }
    @DeleteMapping("/{voucherId}")
    public ResponseModel createVoucher(@PathVariable("voucherId") String voucherId)
    {
        return voucherService.deleteVoucher(voucherId);
    }
    @PutMapping
    public ResponseModel updateVoucher(@RequestBody VoucherDTO dto)
    {
        return voucherService.updateVoucher(dto);
    }
    @GetMapping("/all")
    public ResponseModel getAllVouchersSortedByDiscount() {
        return voucherService.getAllVouchersSortedByDiscount();
    }
}
