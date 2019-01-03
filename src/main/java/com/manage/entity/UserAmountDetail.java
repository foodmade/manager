package com.manage.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.manage.common.commonUtil.DateUtil;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * USER_AMOUNT_DETAIL
 * @author 
 */
public class UserAmountDetail implements Serializable {
    private Long delid;

    /**
     * 用户ID
     */
    private Long userid;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date time;

    /**
     * 分红
     */
    private Float comamount;

    private Float amount;

    private Float tax;

    private Float repeatamount;

    private Float relamount;

    private static final long serialVersionUID = 1L;

    private Integer year;
    private Integer month;

    // 最小时间
    private  Date minDate;
    // 最大时间
    private  Date maxDate;

    private Double daySum;

    public void setMonth(Integer month) {
        this.month = month;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public Integer getYear() {
        return year;
    }

    public Date getMonthBeginDate() {
        if(month==null){
            return null;
        }
        return DateUtil.getBeginDayOfMonth(this.year,this.month);
    }
    public Date getMonthendDate() {
        if(month==null){
            return null;
        }
        return DateUtil.getLastDayOfMonth(this.year,this.month);
    }


    public Long getDelid() {
        return delid;
    }

    public void setDelid(Long delid) {
        this.delid = delid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Float getComamount() {
        return comamount;
    }

    public void setComamount(Float comamount) {
        this.comamount = comamount;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Float getTax() {
        return tax;
    }

    public void setTax(Float tax) {
        this.tax = tax;
    }

    public Float getRepeatamount() {
        return repeatamount;
    }

    public void setRepeatamount(Float repeatamount) {
        this.repeatamount = repeatamount;
    }

    public Float getRelamount() {
        return relamount;
    }

    public void setRelamount(Float relamount) {
        this.relamount = relamount;
    }

    public Double getDaySum() {
        return daySum;
    }

    public void setDaySum(Double daySum) {
        this.daySum = daySum;
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
        UserAmountDetail other = (UserAmountDetail) that;
        return (this.getDelid() == null ? other.getDelid() == null : this.getDelid().equals(other.getDelid()))
            && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
            && (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()))
            && (this.getComamount() == null ? other.getComamount() == null : this.getComamount().equals(other.getComamount()))
            && (this.getAmount() == null ? other.getAmount() == null : this.getAmount().equals(other.getAmount()))
            && (this.getTax() == null ? other.getTax() == null : this.getTax().equals(other.getTax()))
            && (this.getRepeatamount() == null ? other.getRepeatamount() == null : this.getRepeatamount().equals(other.getRepeatamount()))
            && (this.getRelamount() == null ? other.getRelamount() == null : this.getRelamount().equals(other.getRelamount()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDelid() == null) ? 0 : getDelid().hashCode());
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        result = prime * result + ((getComamount() == null) ? 0 : getComamount().hashCode());
        result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
        result = prime * result + ((getTax() == null) ? 0 : getTax().hashCode());
        result = prime * result + ((getRepeatamount() == null) ? 0 : getRepeatamount().hashCode());
        result = prime * result + ((getRelamount() == null) ? 0 : getRelamount().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", delid=").append(delid);
        sb.append(", userid=").append(userid);
        sb.append(", time=").append(time);
        sb.append(", comamount=").append(comamount);
        sb.append(", amount=").append(amount);
        sb.append(", tax=").append(tax);
        sb.append(", repeatamount=").append(repeatamount);
        sb.append(", relamount=").append(relamount);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        // 获取当前年份、月份、日期
        Calendar cale = null;
        cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        int month = cale.get(Calendar.MONTH) + 1;
        int day = cale.get(Calendar.DATE);
        int hour = cale.get(Calendar.HOUR_OF_DAY);
        int minute = cale.get(Calendar.MINUTE);
        int second = cale.get(Calendar.SECOND);
        int dow = cale.get(Calendar.DAY_OF_WEEK);
        int dom = cale.get(Calendar.DAY_OF_MONTH);
        int doy = cale.get(Calendar.DAY_OF_YEAR);

        System.out.println("Current Date: " + cale.getTime());
        System.out.println("Year: " + year);
        System.out.println("Month: " + month);
        System.out.println("Day: " + day);
        System.out.println("Hour: " + hour);
        System.out.println("Minute: " + minute);
        System.out.println("Second: " + second);
        System.out.println("Day of Week: " + dow);
        System.out.println("Day of Month: " + dom);
        System.out.println("Day of Year: " + doy);


        // 获取当月第一天和最后一天
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String firstday, lastday;
        // 获取前月的第一天
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        firstday = format.format(cale.getTime());
        // 获取前月的最后一天
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        lastday = format.format(cale.getTime());
        System.out.println("本月第一天和最后一天分别是 ： " + firstday + " and " + lastday);

        // 获取当前日期字符串
        Date d = new Date();
        System.out.println("当前日期字符串1：" + format.format(d));
        System.out.println("当前日期字符串2：" + year + "/" + month + "/" + day + " "
                + hour + ":" + minute + ":" + second);
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public Date getMinDate() {
        return minDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }
}