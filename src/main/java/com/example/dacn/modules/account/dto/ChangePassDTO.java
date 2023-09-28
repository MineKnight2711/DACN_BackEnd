package com.example.dacn.modules.account.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class ChangePassDTO {
    private String email;
    private String oldPassword;
    private String newPassword;
}
