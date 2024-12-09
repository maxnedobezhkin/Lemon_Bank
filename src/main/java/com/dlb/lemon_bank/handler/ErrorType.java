package com.dlb.lemon_bank.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

// TODO Добавить кастомные параметры в сообщения
@AllArgsConstructor
@Getter
public enum ErrorType {
    UNAUTHORIZED("Unauthorized", HttpStatus.UNAUTHORIZED),
    USER_NOT_FOUND("User not found", HttpStatus.BAD_REQUEST);

    public final String message;
    public final HttpStatus status;
}
