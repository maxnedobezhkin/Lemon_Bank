package com.dlb.lemon_bank.domain.repository;

import com.dlb.lemon_bank.domain.entity.HistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<HistoryEntity, Integer> {

}
