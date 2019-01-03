package com.manage.common.model;

import com.manage.common.enums.ExceptionEnum;

public class BaseResult {

    private String code = "";

    private String message = "";

    private Object responseBody = null;

    public BaseResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseResult(String code, String message, Object responseBody) {

        this.code = code;
        this.message = message;
        this.responseBody = responseBody;
    }

    public BaseResult(ExceptionEnum exceptionEnum){
        this.message = exceptionEnum.getMessage();
        this.code = exceptionEnum.getCode();
    }

    public BaseResult(Object responseBody) {
        this.code = ExceptionEnum.SUCCESS.getCode();
        this.message = ExceptionEnum.SUCCESS.getMessage();
        this.responseBody = responseBody;
    }

    public BaseResult(){}

    public boolean isOk(){
        return this.code.equals(ExceptionEnum.SUCCESS.getCode());
    }

    public static BaseResult success(Object responseBody){
        BaseResult result = new BaseResult();
        result.setCode(ExceptionEnum.SUCCESS.getCode());
        result.setMessage(ExceptionEnum.SUCCESS.getMessage());
        result.setResponseBody(responseBody);
        return result;
    }

    public static BaseResult makeExceptionResult(String code,String message){
        BaseResult result = new BaseResult();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(Object responseBody) {
        this.responseBody = responseBody;
    }
}
