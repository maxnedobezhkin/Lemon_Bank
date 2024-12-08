package com.dlb.lemon_bank.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

// TODO Добавить кастомные параметры в сообщения
@AllArgsConstructor
@Getter
public enum ErrorType {
    USER_NOT_FOUND("User not found", HttpStatus.UNAUTHORIZED);

    public final String message;
    public final HttpStatus status;
}
