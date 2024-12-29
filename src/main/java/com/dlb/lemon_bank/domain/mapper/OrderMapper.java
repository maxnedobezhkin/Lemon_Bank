package com.dlb.lemon_bank.domain.mapper;


import com.dlb.lemon_bank.domain.dto.OrderResponseDto;
import com.dlb.lemon_bank.domain.dto.OrderWebhookDto;
import com.dlb.lemon_bank.domain.entity.OrdersEntity;
import com.dlb.lemon_bank.domain.service.OrderMapperService;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = OrderMapperService.class
)
public interface OrderMapper {
    @Mapping(target = "employee", source = "orderDto.email", qualifiedByName = "mapEmailToId")
    @Mapping(target = "tildaId", source = "orderDto.id")
    @Mapping(target = "total", source = "orderDto.total")
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "id", ignore = true)
    OrdersEntity toOrderEntity(OrderWebhookDto orderDto);

    @Mapping(target = "email", source = "orderEntity.employee", qualifiedByName = "mapEmployeeToEmail")
    OrderResponseDto toOrderResponseDto(OrdersEntity orderEntity);

    List<OrderResponseDto> toOrderDtoList(List<OrdersEntity> ordersEntities);

}
