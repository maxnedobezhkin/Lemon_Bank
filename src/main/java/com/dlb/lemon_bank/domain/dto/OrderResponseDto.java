package com.dlb.lemon_bank.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderResponseDto extends OrderBaseDto {
    private Integer id;

}
