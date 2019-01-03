package com.manage.entity;

import java.io.Serializable;

/**
 * USER_BANKCORDS
 * @author 
 */
public class UserBankcords implements Serializable {
    /**
     * ID
     */
    private Long cardid;

    /**
     * 用户ID
     */
    private Long userid;

    private String bankname;

    private Integer bankno;

    private static final long serialVersionUID = 1L;

    public Long getCardid() {
        return cardid;
    }

    public void setCardid(Long cardid) {
        this.cardid = cardid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public Integer getBankno() {
        return bankno;
    }

    public void setBankno(Integer bankno) {
        this.bankno = bankno;
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
        UserBankcords other = (UserBankcords) that;
        return (this.getCardid() == null ? other.getCardid() == null : this.getCardid().equals(other.getCardid()))
            && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
            && (this.getBankname() == null ? other.getBankname() == null : this.getBankname().equals(other.getBankname()))
            && (this.getBankno() == null ? other.getBankno() == null : this.getBankno().equals(other.getBankno()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCardid() == null) ? 0 : getCardid().hashCode());
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        result = prime * result + ((getBankname() == null) ? 0 : getBankname().hashCode());
        result = prime * result + ((getBankno() == null) ? 0 : getBankno().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", cardid=").append(cardid);
        sb.append(", userid=").append(userid);
        sb.append(", bankname=").append(bankname);
        sb.append(", bankno=").append(bankno);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}