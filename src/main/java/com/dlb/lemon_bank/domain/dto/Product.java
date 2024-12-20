package com.dlb.lemon_bank.domain.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class Product implements Serializable {
    private String title;
    private Integer count;
    private Integer cost;
    private String currency;
}
