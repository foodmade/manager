package com.manage.vo.in;

public class UserInRequest {

    private String startTime;

    private String endTime;

    private String toppid;

    private Long userid;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getToppid() {
        return toppid;
    }

    public void setToppid(String toppid) {
        this.toppid = toppid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }
}
