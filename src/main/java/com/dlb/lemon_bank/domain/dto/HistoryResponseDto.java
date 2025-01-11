package com.dlb.lemon_bank.domain.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class HistoryResponseDto {
    private Integer id;
    private Integer userId;
    private Integer adminId;
    private LocalDate date;
    private String type;
    private String comment;
    private Integer orderId;
    private String currency;
    private Integer value;
}
