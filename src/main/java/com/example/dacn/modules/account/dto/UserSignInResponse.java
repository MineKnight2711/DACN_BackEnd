package com.example.dacn.modules.account.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserSignInResponse {
    private String kind;
    private String localId;
    private String email;
    private String displayName;
    private String idToken;
    private boolean registered;
}
