package com.manage.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DROA
 * @author 
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Droa implements Serializable {
    private Long droaid;

    /**
     * 提现申请用户
     */
    private Long userid;
    /**
     * 提现申请用户
     */
    private String userno;

    /**
     * 提现处理操作员
     */
    private Long exeuserid;

    /**
     * APPLY(1,"待处理"),EXE(2,"已处理");
     */
    private Integer state;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date exetime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date applytime;

    private Float droaamount;

    private Double balance;

    private String crod;

    private static final long serialVersionUID = 1L;

    public Long getDroaid() {
        return droaid;
    }

    public void setDroaid(Long droaid) {
        this.droaid = droaid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getExeuserid() {
        return exeuserid;
    }

    public void setExeuserid(Long exeuserid) {
        this.exeuserid = exeuserid;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getExetime() {
        return exetime;
    }

    public void setExetime(Date exetime) {
        this.exetime = exetime;
    }

    public Date getApplytime() {
        return applytime;
    }

    public void setApplytime(Date applytime) {
        this.applytime = applytime;
    }

    public Float getDroaamount() {
        return droaamount;
    }

    public void setDroaamount(Float droaamount) {
        this.droaamount = droaamount;
    }

    public void setCrod(String crod) {
        this.crod = crod;
    }

    public String getCrod() {
        return crod;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Droa other = (Droa) that;
        return (this.getDroaid() == null ? other.getDroaid() == null : this.getDroaid().equals(other.getDroaid()))
            && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
            && (this.getExeuserid() == null ? other.getExeuserid() == null : this.getExeuserid().equals(other.getExeuserid()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
            && (this.getExetime() == null ? other.getExetime() == null : this.getExetime().equals(other.getExetime()))
            && (this.getApplytime() == null ? other.getApplytime() == null : this.getApplytime().equals(other.getApplytime()))
            && (this.getDroaamount() == null ? other.getDroaamount() == null : this.getDroaamount().equals(other.getDroaamount()))
            && (this.getCrod() == null ? other.getCrod() == null : this.getCrod().equals(other.getCrod()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDroaid() == null) ? 0 : getDroaid().hashCode());
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        result = prime * result + ((getExeuserid() == null) ? 0 : getExeuserid().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getExetime() == null) ? 0 : getExetime().hashCode());
        result = prime * result + ((getApplytime() == null) ? 0 : getApplytime().hashCode());
        result = prime * result + ((getDroaamount() == null) ? 0 : getDroaamount().hashCode());
        result = prime * result + ((getCrod() == null) ? 0 : getCrod().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", droaid=").append(droaid);
        sb.append(", userid=").append(userid);
        sb.append(", exeuserid=").append(exeuserid);
        sb.append(", state=").append(state);
        sb.append(", exetime=").append(exetime);
        sb.append(", applytime=").append(applytime);
        sb.append(", droaamount=").append(droaamount);
        sb.append(", crod=").append(crod);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public void setApplyTimeString(String applyTimeString) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if(StringUtils.isEmpty(applyTimeString))
            return;
        this.applytime = format.parse(applyTimeString);
    }

    public void setUserno(String userno) {
        this.userno = userno;
    }

    public String getUserno() {
        return userno;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getBalance() {
        return balance;
    }
}