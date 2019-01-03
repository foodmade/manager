package com.manage.vo;

import com.manage.entity.UserLevelLog;

import java.text.SimpleDateFormat;

public class UserLevelLogView extends UserLevelLog {

    private  String username;

    private  String levelname;

    private Integer daynum;

    private Integer actDaynum;

    private Double amount;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getDaynum() {
        return daynum;
    }

    public String getLevelname() {
        return levelname;
    }

    public String getUsername() {
        return username;
    }

    public void setDaynum(Integer daynum) {
        this.daynum = daynum;
    }

    public void setLevelname(String levelname) {
        this.levelname = levelname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setActDaynum(Integer actDaynum) {
        this.actDaynum = actDaynum;
    }

    public Integer getActDaynum() {
        return actDaynum;
    }
}

