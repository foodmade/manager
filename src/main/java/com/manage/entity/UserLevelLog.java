package com.manage.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * USER_LEVEL_LOG
 * @author 
 */
public class UserLevelLog implements Serializable {
    private Long logid;

    /**
     * ID
     */
    private Long userid;

    /**
     * 调整后等级
     */
    private Long levelid;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date time;

    private String reason;

    private static final long serialVersionUID = 1L;

    public Long getLogid() {
        return logid;
    }

    public void setLogid(Long logid) {
        this.logid = logid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getLevelid() {
        return levelid;
    }

    public void setLevelid(Long levelid) {
        this.levelid = levelid;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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
        UserLevelLog other = (UserLevelLog) that;
        return (this.getLogid() == null ? other.getLogid() == null : this.getLogid().equals(other.getLogid()))
            && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
            && (this.getLevelid() == null ? other.getLevelid() == null : this.getLevelid().equals(other.getLevelid()))
            && (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()))
            && (this.getReason() == null ? other.getReason() == null : this.getReason().equals(other.getReason()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getLogid() == null) ? 0 : getLogid().hashCode());
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        result = prime * result + ((getLevelid() == null) ? 0 : getLevelid().hashCode());
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        result = prime * result + ((getReason() == null) ? 0 : getReason().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", logid=").append(logid);
        sb.append(", userid=").append(userid);
        sb.append(", levelid=").append(levelid);
        sb.append(", time=").append(time);
        sb.append(", reason=").append(reason);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}