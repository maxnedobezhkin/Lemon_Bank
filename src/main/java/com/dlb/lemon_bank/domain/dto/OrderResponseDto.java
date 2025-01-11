package com.dlb.lemon_bank.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class OrderResponseDto {
    private Integer id;
    private String date;
    private String items;
    private String tildaId;
    private String total;
    private String email;
    private String status;
}
