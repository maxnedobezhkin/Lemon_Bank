package com.dlb.lemon_bank.controller;

import com.dlb.lemon_bank.domain.dto.UserRequestDto;
import com.dlb.lemon_bank.domain.dto.UserResponseDto;
import com.dlb.lemon_bank.service.UserService;
import java.awt.print.Pageable;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/employers")
    public List<UserResponseDto> getEmployers(@RequestParam("offset") Integer offset,
        @RequestParam("limit") Integer limit) {
        return userService.getAllUsers(offset, limit);
    }

    @GetMapping("/employers/{id}")
    public UserResponseDto getEmployeeById(@PathVariable("id") Integer id) {
        return userService.getUserById(id);
    }

    @GetMapping("/employers/open-by-email/{email}")
    public UserResponseDto getEmployeeByEmail(@PathVariable("email") String email) {
        return userService.getUserByEmail(email);
    }

}
