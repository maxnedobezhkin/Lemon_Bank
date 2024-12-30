package com.dlb.lemon_bank.controller;

import com.dlb.lemon_bank.domain.dto.HistoryResponseDto;
import com.dlb.lemon_bank.domain.dto.OrderResponseDto;
import com.dlb.lemon_bank.domain.dto.OrderUpdateStatusDto;
import com.dlb.lemon_bank.domain.dto.OrderWebhookDto;
import com.dlb.lemon_bank.service.HistoryService;
import com.dlb.lemon_bank.service.OrdersService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/history")
@RequiredArgsConstructor
public class HistoryController {
    private final HistoryService historyService;

    @GetMapping("/find-by-date-and-param")
    public List<HistoryResponseDto> getOrderById(@RequestParam("dateFrom") String dateFrom,
                                         @RequestParam("dateTo") String dateTo,
                                         @RequestParam("searchParameter") String searchParameter) {
        return historyService.getHistoryByDateAndParam(dateFrom, dateTo, searchParameter);
    }

    @GetMapping("/find-by-id")
    public List<HistoryResponseDto> getOrderById(@RequestParam("id") Integer id) {
        return historyService.getHistoryById(id);
    }


}
