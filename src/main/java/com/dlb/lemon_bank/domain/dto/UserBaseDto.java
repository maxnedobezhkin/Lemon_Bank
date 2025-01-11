package com.dlb.lemon_bank.domain.dto;

import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class UserBaseDto {
    private String firstName;
    private String lastName;
    private String email;
    private Integer lemons;
    private Integer diamonds;
    private String userRole;
    @Nullable
    private String jobTitle;
    @Nullable
    private Boolean isActive;
}
