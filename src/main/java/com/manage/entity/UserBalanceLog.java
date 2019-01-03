package com.manage.entity;

import java.io.Serializable;

/**
 * USER_BALANCE_LOG
 * @author 
 */
public class UserBalanceLog implements Serializable {
    /**
     * 日志id
     */
    private Long logid;

    /**
     * 余额id
     */
    private Long balid;

    /**
     * 金额
     */
    private Float amount;

    /**
     * 1：收入、2：支出
     */
    private Integer type;

    /**
     * 1：日工资+分红、2：提现
     */
    private Integer optiontype;

    /**
     * 更新前余额
     */
    private Float oldbalance;

    private static final long serialVersionUID = 1L;

    public Long getLogid() {
        return logid;
    }

    public void setLogid(Long logid) {
        this.logid = logid;
    }

    public Long getBalid() {
        return balid;
    }

    public void setBalid(Long balid) {
        this.balid = balid;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getOptiontype() {
        return optiontype;
    }

    public void setOptiontype(Integer optiontype) {
        this.optiontype = optiontype;
    }

    public Float getOldbalance() {
        return oldbalance;
    }

    public void setOldbalance(Float oldbalance) {
        this.oldbalance = oldbalance;
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
        UserBalanceLog other = (UserBalanceLog) that;
        return (this.getLogid() == null ? other.getLogid() == null : this.getLogid().equals(other.getLogid()))
            && (this.getBalid() == null ? other.getBalid() == null : this.getBalid().equals(other.getBalid()))
            && (this.getAmount() == null ? other.getAmount() == null : this.getAmount().equals(other.getAmount()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getOptiontype() == null ? other.getOptiontype() == null : this.getOptiontype().equals(other.getOptiontype()))
            && (this.getOldbalance() == null ? other.getOldbalance() == null : this.getOldbalance().equals(other.getOldbalance()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getLogid() == null) ? 0 : getLogid().hashCode());
        result = prime * result + ((getBalid() == null) ? 0 : getBalid().hashCode());
        result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getOptiontype() == null) ? 0 : getOptiontype().hashCode());
        result = prime * result + ((getOldbalance() == null) ? 0 : getOldbalance().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", logid=").append(logid);
        sb.append(", balid=").append(balid);
        sb.append(", amount=").append(amount);
        sb.append(", type=").append(type);
        sb.append(", optiontype=").append(optiontype);
        sb.append(", oldbalance=").append(oldbalance);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}