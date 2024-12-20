package com.dlb.lemon_bank.controller;

import com.dlb.lemon_bank.domain.dto.OrderBaseDto;
import com.dlb.lemon_bank.domain.dto.OrderResponseDto;
import com.dlb.lemon_bank.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrdersController {
    private final OrdersService ordersService;

//    @PostMapping("/create")
//    public OrderResponseDto createNewOrder(@RequestBody OrderBaseDto orderDto) {
//        return ordersService.createNewOrder(orderDto);
//    }

}
