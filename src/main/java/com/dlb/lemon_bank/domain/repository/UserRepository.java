package com.dlb.lemon_bank.domain.repository;

import com.dlb.lemon_bank.domain.entity.UserEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByEmailContaining(String email);
    List<UserEntity> findByFirstName(String firstName);
    List<UserEntity> findByLastName(String lastName);
    List<UserEntity> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);


}
