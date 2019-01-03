package com.manage.entity;

import com.manage.common.commonUtil.CustomDateSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;
import java.util.Date;

/**
 * USERS
 * @author 
 */
public class Users extends BaseEntity implements Serializable {
    /**
     * ID
     */
    private Long userid;

    /**
     * 推荐人ID
     */
    private Long pid;

    private Long perid;

    private Long levelid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户编号
     */
    private String userno;

    /**
     * 支付密码
     */
    private String paypwd = "888888";

    /**
     * 用户密码
     */
    private String pwd = "123456";

    /**
     * 身份证号
     */
    private String idcord;

    /**
     * 地址
     */
    private String address;

    /**
     * 注册时间
     */
    private Date registtime;

    /**
     * 是否是新用户:0-老；1-新
     */
    private Integer isolduser;

    /**
     * 手机号
     */
    private String tleno;

    /**
     * 用户状态:0：正常，2：销户（对于删除）
     */
    private Integer state;

    private String headiconurl;

    /**
     * 所属区域
     */
    private Integer area;

    /**
     * 顶层推荐人id
     */
    private String toppid;
    /**
     * 每月应发的工资之和
     */
    private Double monthAmount;

    private static final long serialVersionUID = 1L;

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getPerid() {
        return perid;
    }

    public void setPerid(Long perid) {
        this.perid = perid;
    }

    public Long getLevelid() {
        return levelid;
    }

    public void setLevelid(Long levelid) {
        this.levelid = levelid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserno() {
        return userno;
    }

    public void setUserno(String userno) {
        this.userno = userno;
    }

    public String getPaypwd() {
        return paypwd;
    }

    public void setPaypwd(String paypwd) {
        this.paypwd = paypwd;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getIdcord() {
        return idcord;
    }

    public void setIdcord(String idcord) {
        this.idcord = idcord;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getRegisttime() {
        return registtime;
    }

    public void setRegisttime(Date registtime) {
        this.registtime = registtime;
    }

    public Integer getIsolduser() {
        return isolduser;
    }

    public void setIsolduser(Integer isolduser) {
        this.isolduser = isolduser;
    }

    public String getTleno() {
        return tleno;
    }

    public void setTleno(String tleno) {
        this.tleno = tleno;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getHeadiconurl() {
        return headiconurl;
    }

    public void setHeadiconurl(String headiconurl) {
        this.headiconurl = headiconurl;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getToppid() {
        return toppid;
    }

    public void setToppid(String toppid) {
        this.toppid = toppid;
    }

    public Double getMonthAmount() {
        return monthAmount;
    }

    public void setMonthAmount(Double monthAmount) {
        this.monthAmount = monthAmount;
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
        Users other = (Users) that;
        return (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
            && (this.getPid() == null ? other.getPid() == null : this.getPid().equals(other.getPid()))
            && (this.getPerid() == null ? other.getPerid() == null : this.getPerid().equals(other.getPerid()))
            && (this.getLevelid() == null ? other.getLevelid() == null : this.getLevelid().equals(other.getLevelid()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getUserno() == null ? other.getUserno() == null : this.getUserno().equals(other.getUserno()))
            && (this.getPaypwd() == null ? other.getPaypwd() == null : this.getPaypwd().equals(other.getPaypwd()))
            && (this.getPwd() == null ? other.getPwd() == null : this.getPwd().equals(other.getPwd()))
            && (this.getIdcord() == null ? other.getIdcord() == null : this.getIdcord().equals(other.getIdcord()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getRegisttime() == null ? other.getRegisttime() == null : this.getRegisttime().equals(other.getRegisttime()))
            && (this.getIsolduser() == null ? other.getIsolduser() == null : this.getIsolduser().equals(other.getIsolduser()))
            && (this.getTleno() == null ? other.getTleno() == null : this.getTleno().equals(other.getTleno()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
            && (this.getHeadiconurl() == null ? other.getHeadiconurl() == null : this.getHeadiconurl().equals(other.getHeadiconurl()))
            && (this.getArea() == null ? other.getArea() == null : this.getArea().equals(other.getArea()))
            && (this.getToppid() == null ? other.getToppid() == null : this.getToppid().equals(other.getToppid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        result = prime * result + ((getPid() == null) ? 0 : getPid().hashCode());
        result = prime * result + ((getPerid() == null) ? 0 : getPerid().hashCode());
        result = prime * result + ((getLevelid() == null) ? 0 : getLevelid().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getUserno() == null) ? 0 : getUserno().hashCode());
        result = prime * result + ((getPaypwd() == null) ? 0 : getPaypwd().hashCode());
        result = prime * result + ((getPwd() == null) ? 0 : getPwd().hashCode());
        result = prime * result + ((getIdcord() == null) ? 0 : getIdcord().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getRegisttime() == null) ? 0 : getRegisttime().hashCode());
        result = prime * result + ((getIsolduser() == null) ? 0 : getIsolduser().hashCode());
        result = prime * result + ((getTleno() == null) ? 0 : getTleno().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getHeadiconurl() == null) ? 0 : getHeadiconurl().hashCode());
        result = prime * result + ((getArea() == null) ? 0 : getArea().hashCode());
        result = prime * result + ((getToppid() == null) ? 0 : getToppid().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userid=").append(userid);
        sb.append(", pid=").append(pid);
        sb.append(", perid=").append(perid);
        sb.append(", levelid=").append(levelid);
        sb.append(", username=").append(username);
        sb.append(", userno=").append(userno);
        sb.append(", paypwd=").append(paypwd);
        sb.append(", pwd=").append(pwd);
        sb.append(", idcord=").append(idcord);
        sb.append(", address=").append(address);
        sb.append(", registtime=").append(registtime);
        sb.append(", isolduser=").append(isolduser);
        sb.append(", tleno=").append(tleno);
        sb.append(", state=").append(state);
        sb.append(", headiconurl=").append(headiconurl);
        sb.append(", area=").append(area);
        sb.append(", toppid=").append(toppid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}