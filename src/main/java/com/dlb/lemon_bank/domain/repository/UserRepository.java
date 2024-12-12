package com.dlb.lemon_bank.domain.repository;

import com.dlb.lemon_bank.domain.entity.UserEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByEmailContainingAndIsActiveIsTrue(String email);
    List<UserEntity> findByFirstNameContainingOrLastNameContainingAndIsActiveIsTrue(String firstName, String lastName);
    Optional<UserEntity> findByIdAndIsActiveIsTrue(Integer id);
}
