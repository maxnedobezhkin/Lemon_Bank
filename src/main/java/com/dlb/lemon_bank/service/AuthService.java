package com.dlb.lemon_bank.service;

import com.dlb.lemon_bank.domain.dto.JwtRequestDto;
import com.dlb.lemon_bank.domain.dto.JwtResponseDto;
import com.dlb.lemon_bank.domain.dto.AdminRegisterDto;
import com.dlb.lemon_bank.domain.entity.UserEntity;
import com.dlb.lemon_bank.domain.repository.UserRepository;
import com.dlb.lemon_bank.handler.ErrorType;
import com.dlb.lemon_bank.handler.exception.LemonBankException;
import com.dlb.lemon_bank.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Transactional
    public UserEntity registerNewUser(AdminRegisterDto adminRegisterDto) {
        UserEntity newUser = new UserEntity();
        newUser.setEmail(adminRegisterDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(adminRegisterDto.getPassword()));
        return userRepository.save(newUser);
    }

    @Transactional
    public JwtResponseDto getNewUserTokenFromLogin(JwtRequestDto requestDto) {
        UserEntity user = userRepository.findByEmailContainingAndIsActiveIsTrue(requestDto.getEmail())
            .orElseThrow(() -> new LemonBankException(ErrorType.USER_NOT_FOUND));
        return JwtResponseDto.builder()
            .accessToken(jwtUtils.generateToken(user.getId()))
            .build();

    }

}
