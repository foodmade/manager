package com.manage.common.enums;

import com.manage.common.enums.IDGen.IDGenRule;

/**
 * 这儿定义了所有顶层节点基础值的计算节点Id规则
 */
public enum treeNodeRulesEnum {
    STANDARDRULE("0","0","1","2",IDGenRule.class),
    LETTERRULE("A","A","B","C",IDGenRule.class),
    LOWERRULE("a","a","b","c",IDGenRule.class),
    LOWERRULE_1("X","X","Y","Z",IDGenRule.class);
    //顶层节点的基础值
    private String BaseAddress;
    //层级标识符 标识第几层 一半和基础值一样
    private String levelRule;
    //区域标识符 标识当前层的区域
    private String YRule;
    //区域标识符
    private String ZRule;
    //生成id规则
    private Class<?> calculateRule;

    treeNodeRulesEnum(String baseAddress, String levelRule, String YRule, String ZRule,Class<?> calculateRule) {
        BaseAddress = baseAddress;
        this.levelRule = levelRule;
        this.YRule = YRule;
        this.ZRule = ZRule;
        this.calculateRule = calculateRule;
    }

    public String getBaseAddress() {
        return BaseAddress;
    }

    public void setBaseAddress(String baseAddress) {
        BaseAddress = baseAddress;
    }

    public String getLevelRule() {
        return levelRule;
    }

    public void setLevelRule(String levelRule) {
        this.levelRule = levelRule;
    }

    public String getYRule() {
        return YRule;
    }

    public void setYRule(String YRule) {
        this.YRule = YRule;
    }

    public Class<?> getCalculateRule() {
        return calculateRule;
    }

    public String getZRule() {
        return ZRule;
    }

    public void setZRule(String ZRule) {
        this.ZRule = ZRule;
    }

    public void setCalculateRule(Class<?> calculateRule) {
        this.calculateRule = calculateRule;
    }
}
