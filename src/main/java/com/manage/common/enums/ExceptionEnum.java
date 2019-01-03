package com.manage.common.enums;

/**
 * 异常情况枚举实例
 */
public enum ExceptionEnum {

    SUCCESS("200","成功"),
    SERVER_ERR("500","服务器内部错误"),
    NOT_FOUND("404","查找资源失败"),
    PARAMEMPTYPEROR("10002","参数缺失"),
    STRINGISNULL("10003","字符串为空"),
    INVALIDCODE("10004","验证码错误"),
    PASSWORDINVALID("10005","密码错误"),
    TOKENRERROR("10006", "请重新登录"),
    DATABASEERROR("10007","数据库异常"),
    ALREADYACCOUNT("10008","用户名已经被使用"),
    REFERRERNOTFOUND("10009","推荐人不存在"),
    PERMISSIONERR("10010","权限异常"),
    DAYNUMERROR("10011","日期非25日"),
    AMOUNTERROR("10012","金额非500的倍数"),
    EXECUTEFAIL("10013","操作失败"),
    ERRUSERID("10014","异常的用户id"),
    RECOMMENTERR("10015","推荐人已达上限"),
    VALIDATECODEERR("10016","验证码生成失败"),
    AUTHCODE("10017","授权码错误"),
    BALANCEERROR("10018","余额不足"),
    RULEMATCHERROR("501","规则匹配失败"),
    AREAOCCUPY("10019","当前节点已被占用"),
    INVALIDREQUEST("10020","非法操作");


    private String code;

    private String message;

    ExceptionEnum(String code,String message){
        this.code = code;
        this.message = message;
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

}
