package com.dlb.lemon_bank.domain.mapper;


import com.dlb.lemon_bank.domain.dto.HistoryResponseDto;
import com.dlb.lemon_bank.domain.dto.OrderResponseDto;
import com.dlb.lemon_bank.domain.dto.OrderWebhookDto;
import com.dlb.lemon_bank.domain.entity.HistoryEntity;
import com.dlb.lemon_bank.domain.entity.OrdersEntity;
import com.dlb.lemon_bank.domain.service.OrderMapperService;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface HistoryMapper {

    @Mapping(target = "userId", source = "historyEntity.user.id")
    @Mapping(target = "adminId", source = "historyEntity.admin.id")
    @Mapping(target = "orderId", source = "historyEntity.order.id")
    HistoryResponseDto toHistoryResponseDto(HistoryEntity historyEntity);

    List<HistoryResponseDto> toHistoryResponseDtoList(List<HistoryEntity> historyEntity);

}
