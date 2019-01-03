package com.manage.vo;

/**
 * 此类为前端的入参demo
 */
public class LoginParams {

    private String userno;

    private String password;

    private String code;

    public LoginParams(String userno, String password, String code) {
        this.userno = userno;
        this.password = password;
        this.code = code;
    }

    public String getUserName() {
        return userno;
    }

    public void setUserName(String user_name) {
        this.userno = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean invalid(){
        return (userno == null || password == null || code == null);
    }
}
