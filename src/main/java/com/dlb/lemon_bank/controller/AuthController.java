package com.dlb.lemon_bank.controller;

import com.dlb.lemon_bank.domain.dto.JwtRequestDto;
import com.dlb.lemon_bank.domain.dto.JwtResponseDto;
import com.dlb.lemon_bank.domain.dto.UserDto;
import com.dlb.lemon_bank.domain.entity.UserEntity;
import com.dlb.lemon_bank.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public JwtResponseDto getAccessToken(@RequestBody JwtRequestDto requestDto) {
        return authService.getNewUserTokenFromLogin(requestDto);
    }

    @PostMapping("/register")
    public UserEntity registerNewUser(@RequestBody UserDto userDto) {
        return authService.registerNewUser(userDto);
    }

}
