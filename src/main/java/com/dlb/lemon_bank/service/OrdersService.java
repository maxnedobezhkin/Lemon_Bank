package com.dlb.lemon_bank.service;

import com.dlb.lemon_bank.domain.dto.OrderResponseDto;
import com.dlb.lemon_bank.domain.dto.OrderUpdateStatusDto;
import com.dlb.lemon_bank.domain.dto.OrderWebhookDto;
import com.dlb.lemon_bank.domain.entity.OrdersEntity;
import com.dlb.lemon_bank.domain.mapper.OrderMapper;
import com.dlb.lemon_bank.domain.repository.OrdersRepository;
import com.dlb.lemon_bank.handler.ErrorType;
import com.dlb.lemon_bank.handler.exception.LemonBankException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrdersService {
    private final OrderMapper orderMapper;
    private final OrdersRepository ordersRepository;

    public OrderResponseDto createNewOrder(OrderWebhookDto orderDto) {
        OrdersEntity orderEntity = orderMapper.toOrderEntity(orderDto);
        OrdersEntity savedOrder = ordersRepository.save(orderEntity);
        return orderMapper.toOrderResponseDto(savedOrder);
    }

    public OrderResponseDto getOrderById(Integer id) {
        Optional<OrdersEntity> order = ordersRepository.findById(id);
        if (order.isPresent()) {
            return orderMapper.toOrderResponseDto(order.get());
        } else  {
            throw new LemonBankException(ErrorType.ORDER_NOT_FOUND);
        }
    }

    public List<OrderResponseDto> getAllActiveOrders() {
        List<OrdersEntity> allActiveOrders = ordersRepository.findAllActiveOrders();
        return orderMapper.toOrderDtoList(allActiveOrders);
    }

    public OrderResponseDto changeStatusById(OrderUpdateStatusDto orderDto) {
        Optional<OrdersEntity> order = ordersRepository.findById(orderDto.getId());
        if (order.isEmpty()) {
            throw new LemonBankException(ErrorType.ORDER_NOT_FOUND);
        }
        OrdersEntity ordersEntity = order.get();
        ordersEntity.setStatus(orderDto.getStatus());
        OrdersEntity saved = ordersRepository.save(ordersEntity);
        return orderMapper.toOrderResponseDto(saved);
    }
}
