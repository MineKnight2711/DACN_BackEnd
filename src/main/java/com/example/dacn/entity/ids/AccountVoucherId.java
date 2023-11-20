package com.example.dacn.entity.ids;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
@Getter
@Setter
@Embeddable
public class AccountVoucherId implements Serializable {
    @Column(name = "account_id")
    private String account_id;
    @Column(name = "voucher_id")
    private String voucher_id;
}
