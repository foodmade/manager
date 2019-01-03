package com.manage.vo;

public class UpdateModel {
    //新密码
    private String password;
    //支付密码
    private String payPassword;
    //银行卡号
    private String bankno;
    //家庭住址
    private String address;
    //手机号码
    private Long tleno;
    //身份证号码
    private String idcord;
    //用户姓名
    private String username;
    //授权码
    private String authorizationCode;

    public String getAddress() {
        return address;
    }

    public String getIdcord() {
        return idcord;
    }

    public void setIdcord(String idcord) {
        this.idcord = idcord;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBankno() {
        return bankno;
    }

    public void setBankno(String bankno) {
        this.bankno = bankno;
    }

    public Long getTleno() {
        return tleno;
    }

    public void setTleno(Long tleno) {
        this.tleno = tleno;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }
}
