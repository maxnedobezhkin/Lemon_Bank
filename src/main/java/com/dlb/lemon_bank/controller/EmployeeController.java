package com.dlb.lemon_bank.controller;

import com.dlb.lemon_bank.domain.dto.UserDto;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @GetMapping("/employers")
    public List<UserDto> getEmployers() {
        return List.of();
    }

}
