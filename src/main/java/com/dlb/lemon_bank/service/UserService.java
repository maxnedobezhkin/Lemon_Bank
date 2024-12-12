package com.dlb.lemon_bank.service;

import com.dlb.lemon_bank.domain.dto.UserBaseDto;
import com.dlb.lemon_bank.domain.dto.UserCurrencyUpdateDto;
import com.dlb.lemon_bank.domain.dto.UserResponseDto;
import com.dlb.lemon_bank.domain.dto.UserStatusUpdateDto;
import com.dlb.lemon_bank.domain.entity.UserEntity;
import com.dlb.lemon_bank.domain.mapper.UserMapper;
import com.dlb.lemon_bank.domain.repository.UserRepository;
import com.dlb.lemon_bank.handler.ErrorType;
import com.dlb.lemon_bank.handler.exception.LemonBankException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// TODO Сделать общий метод с помощью ссылки на метод (Functional<T>)
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public List<UserResponseDto> getAllUsers(Integer offset, Integer limit) {
        Page<UserEntity> users = userRepository.findAll(PageRequest.of(offset, limit));
        return userMapper.toListUserResponseDto(users);
    }

    @Transactional
    public UserResponseDto getUserById(Integer id) {
        Optional<UserEntity> user = userRepository.findByIdAndIsActiveIsTrue(id);
        if (user.isEmpty()) {
            throw new LemonBankException(ErrorType.USER_NOT_FOUND);
        }
        UserEntity userEntity = user.get();
        return userMapper.toUserResponseDto(userEntity);
    }

    @Transactional
    public UserResponseDto getUserByEmail(String email) {
        Optional<UserEntity> user = userRepository.findByEmailContainingAndIsActiveIsTrue(email);
        if (user.isEmpty()) {
            throw new LemonBankException(ErrorType.USER_NOT_FOUND);
        }
        UserEntity userEntity = user.get();
        return userMapper.toUserResponseDto(userEntity);
    }

    @Transactional
    public List<UserResponseDto> getUserByParameter(String searchParameter) {
        String trimParameter = StringUtils.trimToEmpty(searchParameter);
        if (isEnglishSymbols(trimParameter)) {
            Optional<UserEntity> user = userRepository.findByEmailContainingAndIsActiveIsTrue(trimParameter);
            if (user.isEmpty()) {
                throw new LemonBankException(ErrorType.USER_NOT_FOUND);
            }
            return List.of(userMapper.toUserResponseDto(user.get()));
        }
        List<UserEntity> usersByFirstOrLastName =
            userRepository.findByFirstNameContainingOrLastNameContainingAndIsActiveIsTrue(trimParameter, trimParameter);
        if (usersByFirstOrLastName.isEmpty()) {
            throw new LemonBankException(ErrorType.USER_NOT_FOUND);
        }
        return userMapper.toListUserResponseDto(usersByFirstOrLastName);

    }

    public UserResponseDto postNewUser(UserBaseDto userBaseDto) {
        Optional<UserEntity> existedUser = userRepository.findByEmailContainingAndIsActiveIsTrue(userBaseDto.getEmail());
        if (existedUser.isPresent()) {
            throw new LemonBankException(ErrorType.USER_ALREADY_EXIST);
        }
        UserEntity userEntity = userMapper.toUserEntity(userBaseDto);
        UserEntity saved = userRepository.save(userEntity);
        return userMapper.toUserResponseDto(saved);
    }

    public UserResponseDto updateEmployeeCurrency(Integer id,
        UserCurrencyUpdateDto currencyUpdateDtoDto) {
        Optional<UserEntity> user = userRepository.findByIdAndIsActiveIsTrue(id);
        if (user.isEmpty()) {
            throw new LemonBankException(ErrorType.USER_NOT_FOUND);
        }
        UserEntity userEntity = user.get();
        userEntity.setDiamonds(currencyUpdateDtoDto.getDiamonds());
        userEntity.setLemons(currencyUpdateDtoDto.getLemons());
        UserEntity saved = userRepository.save(userEntity);

        return userMapper.toUserResponseDto(saved);
    }

    private boolean isEnglishSymbols(String value) {
        return value.matches("^[a-zA-Z0-9.@]+$");
    }

    public UserResponseDto updateEmployeeStatus(Integer id, UserStatusUpdateDto statusUpdateDto) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new LemonBankException(ErrorType.USER_NOT_FOUND);
        }
        UserEntity userEntity = user.get();
        userEntity.setIsActive(statusUpdateDto.getIsActive());
        UserEntity saved = userRepository.save(userEntity);

        return userMapper.toUserResponseDto(saved);
    }
}
