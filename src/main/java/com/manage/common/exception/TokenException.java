package com.manage.common.exception;

import com.manage.common.enums.ExceptionEnum;

public class TokenException extends RuntimeException {

    protected String code;

    public TokenException() {
        super();
    }

    public TokenException(String message) {
        super(message);
        this.code = ExceptionEnum.SERVER_ERR.getCode();
    }

    public TokenException(ExceptionEnum exception) {
        super(exception.getMessage());
        this.code = exception.getCode();
    }

    public TokenException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
