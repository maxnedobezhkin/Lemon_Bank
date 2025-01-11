package com.dlb.lemon_bank.handler;

import com.dlb.lemon_bank.domain.dto.ErrorResponseDto;
import com.dlb.lemon_bank.handler.exception.LemonBankException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class LemonBankExceptionHandler{

    @ExceptionHandler(LemonBankException.class)
    public ResponseEntity<ErrorResponseDto> handleError(LemonBankException exception) {
        log.info("Start process exception");
        return ResponseEntity
            .status(exception.errorType.getStatus())
            .body(getErrorResponseDto(exception));
    }

    private ErrorResponseDto getErrorResponseDto(LemonBankException exception) {
        return ErrorResponseDto.builder()
            .error(exception.getMessage())
            .build();
    }

}
