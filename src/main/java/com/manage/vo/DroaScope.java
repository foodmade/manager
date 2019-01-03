package com.manage.vo;

import com.manage.common.commonUtil.DateUtil;
import com.manage.entity.Droa;
import com.manage.entity.Page;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DroaScope extends Droa {

    private Double minDroaAmount;

    private Double maxDroaAmount;

    private String month;

    private Page page = new Page();
    public Double getMaxDroaAmount() {
        return maxDroaAmount;
    }

    public Double getMinDroaAmount() {
        return minDroaAmount;
    }

    public void setMaxDroaAmount(Double maxDroaAmount) {
        this.maxDroaAmount = maxDroaAmount;
    }

    public void setMinDroaAmount(Double minDroaAmount) {
        this.minDroaAmount = minDroaAmount;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getMonth() {
        return month;
    }

    public Date getBebinDate(){
        Date date = null;
        if(!StringUtils.isNotEmpty(this.month)){
            return date;
        }
        String[] split = month.split("-");
        try{
            date = DateUtil.getBeginDayOfMonth(new Integer(split[0]), new Integer(split[1]));
        }catch (Exception e){
            e.printStackTrace();
        }
        return date;
    }
    public Date getEndDate(){
        Date date = null;
        if(!StringUtils.isNotEmpty(this.month)){
            return date;
        }
        String[] split = month.split("-");
        try{
            date = DateUtil.getLastDayOfMonth(new Integer(split[0]),new Integer(split[1]));
        }catch (Exception e){
            e.printStackTrace();
        }
        return date;
    }
}