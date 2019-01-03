package com.manage.common.enums;

public enum DroaState {

    APPLY(1,"待处理"),EXE(2,"已处理");

    private Integer code;

    private String message;

    DroaState(Integer code,String message){
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
