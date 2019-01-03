package com.manage.vo;

import com.manage.entity.Users;

import java.util.Date;

public class UserScope  extends Users {

    private Integer[] userLevls;

    private Date beginDate;

    private Date endDate;

    private String date;

    public void setUserLevls(Integer[] userLevls) {
        this.userLevls = userLevls;
    }

    public Integer[] getUserLevls() {
        return userLevls;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
