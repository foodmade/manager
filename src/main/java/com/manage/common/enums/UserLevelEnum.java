package com.manage.common.enums;

public enum UserLevelEnum {
    LEVLE1(1,"普通会员",0.0,100),
    LEVLE2(2,"员工骨干",190.00,120),
    LEVLE3(3,"组长",390.00,140),
    LEVLE4(4,"班长",690.00,160),
    LEVLE5(5,"主管",1090.00,180),
    LEVLE6(6,"主任",1590.00,200),
    LEVLE7(7,"经理",2190.00,220);

    private Integer levelCode;

    private String levelName;

    private Double amount;

    private Integer daynum;

    public Integer getLevelCode() {
        return levelCode;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelCode(Integer levelCode) {
        this.levelCode = levelCode;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
    UserLevelEnum(Integer levelCode, String levelName, Double amount,Integer daynum){
        this.levelCode=levelCode;
        this.levelName=levelName;
        this.amount=amount;
        this.daynum=daynum;
    }

    public Integer getDaynum() {
        return daynum;
    }

    public void setDaynum(Integer daynum) {
        this.daynum = daynum;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public static UserLevelEnum getAmountBylevelCode(Long levelCode){
        for(UserLevelEnum a : UserLevelEnum.values()){
            if(levelCode == Long.parseLong(a.getLevelCode()+"")){
                return a;
            }
        }
        return null;
    }
}
