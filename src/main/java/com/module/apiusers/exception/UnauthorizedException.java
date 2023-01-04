package com.module.apiusers.exception;

import com.module.apiusers.errorhandler.ErrorCode;
public class UnauthorizedException extends GenericException {

    public UnauthorizedException(ErrorCode errorCode){
        super(errorCode);
    }
}
