package com.manage.common.exception;

import com.manage.common.enums.ExceptionEnum;

public class RuleException extends RuntimeException {

    protected String code;

    public RuleException() {
        super();
    }

    public RuleException(String message) {
        super(message);
        this.code = ExceptionEnum.SERVER_ERR.getCode();
    }

    public RuleException(ExceptionEnum exception) {
        super(exception.getMessage());
        this.code = exception.getCode();
    }

    public RuleException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
