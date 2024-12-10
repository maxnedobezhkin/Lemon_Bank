package com.dlb.lemon_bank.controller;

import com.dlb.lemon_bank.domain.dto.UserBaseDto;
import com.dlb.lemon_bank.domain.dto.UserResponseDto;
import com.dlb.lemon_bank.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/employers/find-by-email-open/{email}")
    public UserResponseDto getEmployeeByEmailOpen(@PathVariable("email") String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/employers/find-by-param")
    public List<UserResponseDto> getEmployeeByEmailOrName(@RequestParam("searchParameter") String searchParameter) {
        return userService.getUserByParameter(searchParameter);
    }

    @PostMapping("/employers")
    public UserResponseDto postNewUser(@RequestBody UserBaseDto userBaseDto) {
        return userService.postNewUser(userBaseDto);
    }

}
