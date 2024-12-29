package com.dlb.lemon_bank.service;

import com.dlb.lemon_bank.domain.entity.HistoryEntity;
import com.dlb.lemon_bank.domain.entity.OrdersEntity;
import com.dlb.lemon_bank.domain.entity.UserEntity;
import com.dlb.lemon_bank.domain.repository.HistoryRepository;
import com.dlb.lemon_bank.domain.repository.UserRepository;
import com.dlb.lemon_bank.handler.ErrorType;
import com.dlb.lemon_bank.handler.exception.LemonBankException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addOrderInHistory(OrdersEntity orders) {
        Integer adminId = Integer.parseInt((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        log.debug("Admin id:{}", adminId);
        Optional<UserEntity> admin = userRepository.findByIdAndIsActiveIsTrue(
            adminId);
        if (admin.isEmpty()) {
            throw new LemonBankException(ErrorType.ADMIN_NOT_FOUND);
        }

        String dateString = orders.getDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.parse(dateString, formatter);
        log.debug("Parse date:{}",date);
        HistoryEntity historyEntity = HistoryEntity.builder()
            .admin(admin.get())
            .user(orders.getEmployee())
            .date(date)
            .type("order")
            .comment("Подтверждение заказа")
            .order(orders)
            .currency("lemons")
            .value(orders.getTotal())
            .build();
        historyRepository.save(historyEntity);
    }

    @Transactional
    public void changeCurrency(UserEntity user, Integer differenceLemons, Integer differenceDiamonds) {
        Integer adminId = Integer.parseInt((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        log.debug("Admin id:{}", adminId);
        Optional<UserEntity> admin = userRepository.findByIdAndIsActiveIsTrue(
            adminId);
        if (admin.isEmpty()) {
            throw new LemonBankException(ErrorType.ADMIN_NOT_FOUND);
        }
        if (differenceLemons != 0) {
            HistoryEntity historyLemons = HistoryEntity.builder()
                .admin(admin.get())
                .user(user)
                .date(LocalDate.now())
                .type("reward")
                .comment("Изменение лимонов")
                .order(null)
                .currency("lemons")
                .value(differenceLemons)
                .build();
            historyRepository.save(historyLemons);
        }

        if (differenceDiamonds != 0) {
            HistoryEntity historyDiamonds = HistoryEntity.builder()
                .admin(admin.get())
                .user(user)
                .date(LocalDate.now())
                .type("reward")
                .comment("Изменение алмазов")
                .order(null)
                .currency("diamonds")
                .value(differenceDiamonds)
                .build();
            historyRepository.save(historyDiamonds);
        }

    }


}
