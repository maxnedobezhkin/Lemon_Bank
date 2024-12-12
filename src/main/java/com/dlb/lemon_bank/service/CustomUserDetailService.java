package com.dlb.lemon_bank.service;

import com.dlb.lemon_bank.domain.entity.UserEntity;
import com.dlb.lemon_bank.domain.repository.UserRepository;
import com.dlb.lemon_bank.handler.ErrorType;
import com.dlb.lemon_bank.handler.exception.LemonBankException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
//@Lazy
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByEmailContainingAndIsActiveIsTrue(email);
        if (user.isPresent())
        {
            UserEntity userObj = user.get();
            return User.builder()
                .username(userObj.getEmail())
                .password(userObj.getPassword())
                .roles(userObj.getUserRole())
                .build();
        }
        else {
            throw new LemonBankException(ErrorType.USER_NOT_FOUND);
        }
    }
}
