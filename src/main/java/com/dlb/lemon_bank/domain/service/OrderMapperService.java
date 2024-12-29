package com.dlb.lemon_bank.domain.service;

import com.dlb.lemon_bank.domain.entity.OrdersEntity;
import com.dlb.lemon_bank.domain.entity.UserEntity;
import com.dlb.lemon_bank.domain.repository.UserRepository;
import com.dlb.lemon_bank.handler.ErrorType;
import com.dlb.lemon_bank.handler.exception.LemonBankException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderMapperService {
    private final UserRepository userRepository;

    @Named("mapEmailToId")
    public UserEntity mapEmailToId(String email) {
        Optional<UserEntity> user = userRepository.findByEmailContainingAndIsActiveIsTrue(email);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new LemonBankException(ErrorType.USER_NOT_FOUND);
        }
    }

    @Named("mapEmployeeToEmail")
    public String mapEmailToId(UserEntity userEntity) {
        return userEntity.getEmail();
    }

}
