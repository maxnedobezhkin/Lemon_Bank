package com.dlb.lemon_bank.handler.exception;

import com.dlb.lemon_bank.handler.ErrorType;

public class LemonBankException extends RuntimeException{

    public final ErrorType errorType;
    public LemonBankException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }
    public LemonBankException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }


}
