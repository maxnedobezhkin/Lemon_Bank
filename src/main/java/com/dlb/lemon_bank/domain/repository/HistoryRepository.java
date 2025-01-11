package com.dlb.lemon_bank.domain.repository;

import com.dlb.lemon_bank.domain.entity.HistoryEntity;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<HistoryEntity, Integer> {

    List<HistoryEntity> findAllByDateBetweenAndUserEmailContaining(LocalDate dateFrom, LocalDate dateTo, String email);
    List<HistoryEntity> findAllByDateBetweenAndUserFirstNameContainingOrUserLastNameContaining(
        LocalDate dateFrom,
        LocalDate dateTo,
        String firstName,
        String lastName);
    List<HistoryEntity> findAllByUserId(Integer id);
}
