package com.dlb.lemon_bank.service;

import com.dlb.lemon_bank.domain.dto.UserRequestDto;
import com.dlb.lemon_bank.domain.dto.UserResponseDto;
import com.dlb.lemon_bank.domain.entity.UserEntity;
import com.dlb.lemon_bank.domain.mapper.UserMapper;
import com.dlb.lemon_bank.domain.repository.UserRepository;
import com.dlb.lemon_bank.handler.ErrorType;
import com.dlb.lemon_bank.handler.exception.LemonBankException;
import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponseDto> getAllUsers(Integer offset, Integer limit) {
        Page<UserEntity> users = userRepository.findAll(PageRequest.of(offset, limit));
        return userMapper.toListUserResponseDto(users);
    }

    public UserResponseDto getUserById(Integer id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new LemonBankException(ErrorType.USER_NOT_FOUND);
        }
        UserEntity userEntity = user.get();
        return userMapper.toUserResponseDto(userEntity);
    }

    public UserResponseDto getUserByEmail(String email) {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new LemonBankException(ErrorType.USER_NOT_FOUND);
        }
        UserEntity userEntity = user.get();
        return userMapper.toUserResponseDto(userEntity);
    }

}
