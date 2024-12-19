package com.dlb.lemon_bank.domain.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
@Builder
public class StatResponseDto {
    private Integer users;
    private Integer lemons;
    private Integer diamonds;
}
