package com.manage.entity;

import java.io.Serializable;

/**
 * USER_LEVEL
 * @author 
 */
public class UserLevel implements Serializable {
    private Long levelid;

    /**
     * 等级
     */
    private String levelname;

    private Float amount;

    /**
     * 能够领工资的最大天数
     */
    private Integer daynum;
    /**
     * 人数最小
     */
    private Integer unums;
    /**
     * 人数最大
     */
    private Integer unume;
    private static final long serialVersionUID = 1L;

    public Long getLevelid() {
        return levelid;
    }

    public void setLevelid(Long levelid) {
        this.levelid = levelid;
    }

    public String getLevelname() {
        return levelname;
    }

    public void setLevelname(String levelname) {
        this.levelname = levelname;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Integer getDaynum() {
        return daynum;
    }

    public void setDaynum(Integer daynum) {
        this.daynum = daynum;
    }

    public void setUnume(Integer unume) {
        this.unume = unume;
    }

    public void setUnums(Integer unums) {
        this.unums = unums;
    }

    public Integer getUnume() {
        return unume;
    }

    public Integer getUnums() {
        return unums;
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
        UserLevel other = (UserLevel) that;
        return (this.getLevelid() == null ? other.getLevelid() == null : this.getLevelid().equals(other.getLevelid()))
            && (this.getLevelname() == null ? other.getLevelname() == null : this.getLevelname().equals(other.getLevelname()))
            && (this.getAmount() == null ? other.getAmount() == null : this.getAmount().equals(other.getAmount()))
            && (this.getDaynum() == null ? other.getDaynum() == null : this.getDaynum().equals(other.getDaynum()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getLevelid() == null) ? 0 : getLevelid().hashCode());
        result = prime * result + ((getLevelname() == null) ? 0 : getLevelname().hashCode());
        result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
        result = prime * result + ((getDaynum() == null) ? 0 : getDaynum().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", levelid=").append(levelid);
        sb.append(", levelname=").append(levelname);
        sb.append(", amount=").append(amount);
        sb.append(", daynum=").append(daynum);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}