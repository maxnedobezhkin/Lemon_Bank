package com.dlb.lemon_bank.service;

import com.dlb.lemon_bank.domain.dto.StatResponseDto;
import com.dlb.lemon_bank.domain.dto.UserBaseDto;
import com.dlb.lemon_bank.domain.dto.UserCurrencyMultipleUpdateDto;
import com.dlb.lemon_bank.domain.dto.UserCurrencyUpdateDto;
import com.dlb.lemon_bank.domain.dto.UserResponseDto;
import com.dlb.lemon_bank.domain.dto.UserStatusMultipleUpdateDto;
import com.dlb.lemon_bank.domain.dto.UserStatusUpdateDto;
import com.dlb.lemon_bank.domain.entity.UserEntity;
import com.dlb.lemon_bank.domain.mapper.UserMapper;
import com.dlb.lemon_bank.domain.repository.UserRepository;
import com.dlb.lemon_bank.handler.ErrorType;
import com.dlb.lemon_bank.handler.exception.LemonBankException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// TODO Сделать общий метод с помощью ссылки на метод (Functional<T>)
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final HistoryService historyService;

    @Transactional
    public List<UserResponseDto> getAllUsers(Integer offset, Integer limit) {
        Page<UserEntity> users = userRepository.findAllAndIsActiveIsTrue(PageRequest.of(offset, limit));
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
        log.info("Start find by param:{}", searchParameter);
        String trimParameter = StringUtils.trimToEmpty(searchParameter);
        log.info("Check is English Symbols");
        if (isEnglishSymbols(trimParameter)) {
            log.info("Find by email");
            Optional<UserEntity> user = userRepository.findByEmailContainingAndIsActiveIsTrue(trimParameter);
            if (user.isEmpty()) {
                throw new LemonBankException(ErrorType.USER_NOT_FOUND);
            }
            return List.of(userMapper.toUserResponseDto(user.get()));
        }
        log.info("Find by Name");
        List<UserEntity> usersByFirstOrLastName =
            userRepository.findByFirstNameContainingOrLastNameContainingAndIsActiveIsTrue(trimParameter, trimParameter);
        if (usersByFirstOrLastName.isEmpty()) {
            throw new LemonBankException(ErrorType.USER_NOT_FOUND);
        }
        return userMapper.toListUserResponseDto(usersByFirstOrLastName);

    }

    @Transactional
    public UserResponseDto postNewUser(UserBaseDto userBaseDto) {
        Optional<UserEntity> existedUser = userRepository.findByEmailContainingAndIsActiveIsTrue(userBaseDto.getEmail());
        if (existedUser.isPresent()) {
            throw new LemonBankException(ErrorType.USER_ALREADY_EXIST);
        }
        UserEntity userEntity = userMapper.toUserEntity(userBaseDto);
        UserEntity saved = userRepository.save(userEntity);
        return userMapper.toUserResponseDto(saved);
    }

    @Transactional
    public UserResponseDto updateEmployeeCurrency(Integer id,
        UserCurrencyUpdateDto currencyUpdateDtoDto) {
        Optional<UserEntity> user = userRepository.findByIdAndIsActiveIsTrue(id);
        if (user.isEmpty()) {
            throw new LemonBankException(ErrorType.USER_NOT_FOUND);
        }
        UserEntity userEntity = user.get();
        Integer currentLemons = userEntity.getLemons();
        Integer currentDiamonds = userEntity.getDiamonds();
        Integer differenceLemons = currencyUpdateDtoDto.getLemons() - currentLemons;
        Integer differenceDiamonds = currencyUpdateDtoDto.getDiamonds() - currentDiamonds;

        userEntity.setDiamonds(currencyUpdateDtoDto.getDiamonds());
        userEntity.setLemons(currencyUpdateDtoDto.getLemons());
        UserEntity saved = userRepository.save(userEntity);

        historyService.changeCurrency(saved, differenceLemons, differenceDiamonds);


        return userMapper.toUserResponseDto(saved);
    }

    private boolean isEnglishSymbols(String value) {
        return value.matches("^[a-zA-Z0-9.@]+$");
    }

    @Transactional
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

    @Transactional
    public List<Integer> updateCurrencyForMultipleUsers(UserCurrencyMultipleUpdateDto updateDto) {
        List<Integer> userIds = updateDto.getUserIds();
        Integer count = updateDto.getCount();
        List<UserEntity> users = userRepository.findAllByIdInAndIsActiveIsTrue(userIds);
        if (updateDto.getCurrency().equals("lemons")) {
            users
                .forEach(user -> {
                    Integer currentLemons = user.getLemons();
                    user.setLemons(currentLemons + count);
                    UserEntity saved = userRepository.save(user);
                    historyService.changeCurrency(saved, count, 0);
                });

//            userRepository.updateLemonsForIds(count, userIds);
        } else if (updateDto.getCurrency().equals("diamonds")) {
            users
                .forEach(user -> {
                    Integer currentDiamonds = user.getDiamonds();
                    user.setDiamonds(currentDiamonds + count);
                    UserEntity saved = userRepository.save(user);
                    historyService.changeCurrency(saved, 0, count);
                });
//            userRepository.updateDiamondsForIds(count, userIds);
        } else {
            throw new LemonBankException(ErrorType.NOT_CORRECT_CURRENCY);
        }
        return userIds;
    }

    @Transactional
    public List<Integer> updateStatusForMultipleUsers(UserStatusMultipleUpdateDto updateDto) {
        List<Integer> userIds = updateDto.getUserIds();
        boolean isActive = updateDto.getIsActive();
        userRepository.updateIsActiveForIds(isActive, userIds);
        return userIds;
    }

    @Transactional
    public StatResponseDto getAllStatistic() {
        Integer users = userRepository.countAllActiveUsers();
        Integer diamonds = userRepository.countAllDiamonds();
        Integer lemons = userRepository.countAllLemons();
        return StatResponseDto.builder()
            .users(users)
            .diamonds(diamonds)
            .lemons(lemons)
            .build();
    }
}
