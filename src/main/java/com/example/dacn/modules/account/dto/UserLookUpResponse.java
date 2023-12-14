package com.example.dacn.modules.account.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class UserLookUpResponse {
    private List<UserResponse> users;
    @Data
    @Getter
    @Setter
    class UserResponse {
        private String email;
        private boolean emailVerified;
    }
}
