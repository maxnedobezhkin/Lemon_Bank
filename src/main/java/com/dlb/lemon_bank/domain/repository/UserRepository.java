package com.dlb.lemon_bank.domain.repository;

import com.dlb.lemon_bank.domain.entity.UserEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByEmailContainingAndIsActiveIsTrue(String email);
    List<UserEntity> findByFirstNameContainingOrLastNameContainingAndIsActiveIsTrue(String firstName, String lastName);
    Optional<UserEntity> findByIdAndIsActiveIsTrue(Integer id);
    @Modifying
    @Query("update UserEntity u set u.diamonds = ?1 where u.id in ?2")
    void updateDiamondsForIds(Integer currency, List<Integer> userIds);
    @Modifying
    @Query("update UserEntity u set u.lemons = ?1 where u.id in ?2")
    void updateLemonsForIds(Integer currency, List<Integer> userIds);
    @Modifying
    @Query("update UserEntity u set u.isActive = ?1 where u.id in ?2")
    void updateIsActiveForIds(boolean isActive, List<Integer> userIds);
}
