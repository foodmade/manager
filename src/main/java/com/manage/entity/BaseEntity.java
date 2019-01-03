package com.manage.entity;

public class BaseEntity {

    private Integer page;

    private Integer pageSize;

    private String startTime;

    private String endTime;

    private String perUserNo;

    public String getStartTime() {
        return startTime;
    }

    public String getPerUserNo() {
        return perUserNo;
    }

    public void setPerUserNo(String perUserNo) {
        this.perUserNo = perUserNo;
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
}
