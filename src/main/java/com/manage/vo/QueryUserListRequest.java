package com.manage.vo;

public class QueryUserListRequest{

    private String userno;

    private Long userid;

    private String tleno;

    private String username;

    private String startTime;

    private String endTime;

    private Integer page;

    private Integer pageSize;

    private String toppid;

    private Long levelid;

    private Integer state;

    private String pid;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getLevelid() {
        return levelid;
    }

    public void setLevelid(Long levelid) {
        this.levelid = levelid;
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

    public String getTleno() {
        return tleno;
    }

    public void setTleno(String tleno) {
        this.tleno = tleno;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getUserno() {
        return userno;
    }

    public void setUserno(String userno) {
        this.userno = userno;
    }

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
}
