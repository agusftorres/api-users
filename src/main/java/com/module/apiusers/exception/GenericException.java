package com.module.apiusers.exception;

import com.module.apiusers.errorhandler.ErrorCode;

public abstract class GenericException extends RuntimeException{
    private final ErrorCode errorCode;

    public GenericException(ErrorCode errorCode) {
        super(errorCode.getReasonPhrase());
        this.errorCode = errorCode;
    }

    public GenericException(ErrorCode errorCode, String message) {
        super(buildMessage(message, errorCode));
        this.errorCode = errorCode;
    }

    public ErrorCode getCode() {
        return this.errorCode;
    }

    private static String buildMessage(String message, ErrorCode errorCode) {
        return errorCode.getReasonPhrase() + ", " + message;
    }
}
