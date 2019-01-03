package com.manage.entity;

import java.io.Serializable;

/**
 * USER_BALANCE
 * @author 
 */
public class UserBalance implements Serializable {
    private Long balid;

    /**
     * ID
     */
    private Long userid;

    private Float balance;

    private static final long serialVersionUID = 1L;

    public Long getBalid() {
        return balid;
    }

    public void setBalid(Long balid) {
        this.balid = balid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
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
        UserBalance other = (UserBalance) that;
        return (this.getBalid() == null ? other.getBalid() == null : this.getBalid().equals(other.getBalid()))
            && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
            && (this.getBalance() == null ? other.getBalance() == null : this.getBalance().equals(other.getBalance()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBalid() == null) ? 0 : getBalid().hashCode());
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        result = prime * result + ((getBalance() == null) ? 0 : getBalance().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", balid=").append(balid);
        sb.append(", userid=").append(userid);
        sb.append(", balance=").append(balance);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}