package com.manage.common.exception;

import com.manage.common.enums.ExceptionEnum;

public class PermissionException extends RuntimeException{

    protected String code;

    public PermissionException() {
        super();
    }

    public PermissionException(String message) {
        super(message);
        this.code = ExceptionEnum.SERVER_ERR.getCode();
    }

    public PermissionException(ExceptionEnum exception) {
        super(exception.getMessage());
        this.code = exception.getCode();
    }

    public PermissionException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
