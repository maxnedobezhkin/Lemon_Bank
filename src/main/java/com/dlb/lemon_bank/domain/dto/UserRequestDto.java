package com.dlb.lemon_bank.domain.dto;

import lombok.Data;

@Data
public class UserRequestDto {
    private String email;
    private String password;
    private String role;

}
