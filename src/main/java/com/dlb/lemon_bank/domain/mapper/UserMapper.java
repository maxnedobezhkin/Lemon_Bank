package com.dlb.lemon_bank.domain.mapper;


import com.dlb.lemon_bank.domain.dto.UserBaseDto;
import com.dlb.lemon_bank.domain.dto.UserResponseDto;
import com.dlb.lemon_bank.domain.entity.UserEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserResponseDto toUserResponseDto(UserEntity entity);
    List<UserResponseDto> toListUserResponseDto(Page<UserEntity> entities);
    List<UserResponseDto> toListUserResponseDto(List<UserEntity> entities);
    UserEntity toUserEntity(UserBaseDto userBaseDto);

}
