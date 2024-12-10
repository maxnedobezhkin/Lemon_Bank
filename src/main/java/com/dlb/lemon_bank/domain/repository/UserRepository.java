package com.dlb.lemon_bank.domain.repository;

import com.dlb.lemon_bank.domain.entity.UserEntity;
import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByEmail(String email);
    List<UserEntity> findByFirstName(String firstName);
    List<UserEntity> findByLastName(String lastName);


}
