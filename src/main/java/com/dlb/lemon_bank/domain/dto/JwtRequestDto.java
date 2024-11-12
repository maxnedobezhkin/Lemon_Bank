package com.dlb.lemon_bank.domain.dto;

import lombok.Data;

@Data
public class JwtRequestDto {
    private String email;
    private String password;
}
