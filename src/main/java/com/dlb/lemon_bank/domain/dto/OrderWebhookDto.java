package com.dlb.lemon_bank.domain.dto;

import jakarta.persistence.Column;
import java.util.List;
import lombok.Data;

@Data
public class OrderWebhookDto {
    private String date;
    private String items;
    private String id;
    private String total;
    private String email;
}
