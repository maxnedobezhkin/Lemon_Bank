package com.dlb.lemon_bank.domain.dto;

import java.util.List;
import lombok.Data;

@Data
public class OrderBaseDto {
    private Integer total;
    private List<Product> products;
}
