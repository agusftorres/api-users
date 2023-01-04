package com.module.apiusers.exception;

import com.module.apiusers.errorhandler.ErrorCode;

public class BusinessException extends GenericException{
    public BusinessException(ErrorCode errorCode) {
        super(errorCode);
    }
}
