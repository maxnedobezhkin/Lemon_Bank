package com.dlb.lemon_bank.domain.repository;

import com.dlb.lemon_bank.domain.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<OrdersEntity, Integer> {

}
