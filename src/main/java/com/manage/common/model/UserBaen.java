package com.manage.common.model;

/**
 * 用户信息model
 */
public class UserBaen {

    /**
     * 用户名
     */
    private String user_name;

    /**
     * 用户id
     */
    private String user_id;

    /**
     * 用户级别
     */
    private String role;
    /**
     * 余额
     */
    private Double balance;
    /**
     * 登陆时间
     */
    private String login_time;

    /**
     * 注册时间
     */
    private String register_time;

    public UserBaen(String user_name, String user_id, String role, Double balance, String login_time, String register_time) {
        this.user_name = user_name;
        this.user_id = user_id;
        this.role = role;
        this.balance = balance;
        this.login_time = login_time;
        this.register_time = register_time;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLogin_time() {
        return login_time;
    }

    public void setLogin_time(String login_time) {
        this.login_time = login_time;
    }

    public String getRegister_time() {
        return register_time;
    }

    public void setRegister_time(String register_time) {
        this.register_time = register_time;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
