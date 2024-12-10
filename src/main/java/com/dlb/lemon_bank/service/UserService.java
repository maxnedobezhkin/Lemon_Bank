package com.dlb.lemon_bank.service;

import com.dlb.lemon_bank.domain.dto.UserBaseDto;
import com.dlb.lemon_bank.domain.dto.UserResponseDto;
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
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new LemonBankException(ErrorType.USER_NOT_FOUND);
        }
        UserEntity userEntity = user.get();
        return userMapper.toUserResponseDto(userEntity);
    }

    @Transactional
    public UserResponseDto getUserByEmail(String email) {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new LemonBankException(ErrorType.USER_NOT_FOUND);
        }
        UserEntity userEntity = user.get();
        return userMapper.toUserResponseDto(userEntity);
    }

    @Transactional
    public List<UserResponseDto> getUserByParameter(String searchParameter) {
        String trimParameter = StringUtils.trimToEmpty(searchParameter);
        if (isEmail(trimParameter)) {
            Optional<UserEntity> user = userRepository.findByEmail(trimParameter);
            if (user.isEmpty()) {
                throw new LemonBankException(ErrorType.USER_NOT_FOUND);
            }
            return List.of(userMapper.toUserResponseDto(user.get()));
        }
        List<UserEntity> userByFirstName = userRepository.findByFirstName(trimParameter);
        List<UserEntity> userByLastName = userRepository.findByLastName(trimParameter);
        if (userByFirstName.isEmpty() && userByLastName.isEmpty()) {
            throw new LemonBankException(ErrorType.USER_NOT_FOUND);
        }

        if (!userByFirstName.isEmpty()) {
            return userMapper.toListUserResponseDto(userByFirstName);
        }
        return userMapper.toListUserResponseDto(userByLastName);

    }

    private boolean isEmail(String value) {
        return value.matches("^[\\w-\\.]+@[\\w-]+(\\.[\\w-]+)*\\.[a-z]{2,}$");
    }

    public UserResponseDto postNewUser(UserBaseDto userBaseDto) {
        Optional<UserEntity> existedUser = userRepository.findByEmail(userBaseDto.getEmail());
        if (existedUser.isPresent()) {
            throw new LemonBankException(ErrorType.USER_ALREADY_EXIST);
        }
        UserEntity userEntity = userMapper.toUserEntity(userBaseDto);
        UserEntity saved = userRepository.save(userEntity);
        return userMapper.toUserResponseDto(saved);
    }
}
