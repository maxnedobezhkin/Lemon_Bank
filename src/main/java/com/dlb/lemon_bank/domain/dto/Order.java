package com.dlb.lemon_bank.domain.dto;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class Order implements Serializable {
    private Integer total;
    private List<Product> products;
}
