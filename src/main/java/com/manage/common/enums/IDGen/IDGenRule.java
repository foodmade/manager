package com.manage.common.enums.IDGen;

import com.manage.common.enums.ExceptionEnum;
import com.manage.common.exception.RuleException;

/**
 * 标准生成节点ID规则
 */
public class IDGenRule {

    //定义基址表
    char[] baseAddressArray = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
                                'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
                                '0','1','2','3','4','5','6','7','8','9'};


    private String baseAddress;
    //层级标识符 标识第几层 一半和基础值一样  todo:代表一区
    private String levelRule;
    //区域标识符 标识当前层的区域
    private String YRule;
    //todo 代表三区
    private String ZRule;

    public IDGenRule(String baseAddress, String levelRule, String YRule,String ZRule) {
        this.baseAddress = baseAddress;
        this.levelRule = levelRule;
        this.YRule = YRule;
        this.ZRule = ZRule;
    }

    public String invoke(String parentAddress,Integer area){
        //验证规则是否合法
        Boolean valid = invalidRule(parentAddress);
        if(!valid){
            throw new RuleException(ExceptionEnum.RULEMATCHERROR);
        }
        StringBuilder parentBuilder = new StringBuilder(parentAddress);
        if(area==0){
            parentBuilder.append(levelRule);
        }else if(area==1){
            parentBuilder.append(YRule);
        }else if(area==3){
            parentBuilder.append(ZRule);
        }
        return parentBuilder.toString();
    }

    private Boolean invalidRule(String parentAddress) {
        String baseStr = baseAddress.substring(0, 1);
        String parentStr = parentAddress.substring(0,1);
        return baseStr.equals(parentStr);
    }
}
