package com.manage.common.commonUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 所有的redis key在这儿定义
 */
public final class JedisKey {

    static final Logger logger = LoggerFactory.getLogger(JedisKey.class);

    public static final int ONE_SECONDS = 1;

    public static final int ONE_MINUTE = ONE_SECONDS * 60;

    public static final int TEN_MINUTE = ONE_MINUTE * 10;

    public static final int THREE_MINUTE = ONE_MINUTE * 3;

    public static final int HALF_HOUR = TEN_MINUTE * 3;

    public static final int ONE_HOUR = ONE_MINUTE * 60;

    public static final int ONE_DAY = ONE_HOUR * 24;

    public static final int ONE_WEEK = ONE_DAY * 7;

    private static final String PREFIX = "api";

    public static final String CACHE_PREFIX = ":";


    public static final String NODENAME = "nodeIdSet";

    //////////////////////////dataBaseNo////////////////
    public static final Integer TOKENSET = 0;          //存放token
    public static final Integer NODEIDID = 2;          //存放树节点标识


    /**
     * 验证码key
     */
    public static String vatcodeKey(String code) {
        return buildKey("vatCode", code);
    }

    /**
     * 授权码key
     */
    public static String accreditcodeKey(Long userId){
        return buildKey("accredit",userId);
    }

    public static String userTokenKey(Integer userId) {
        return buildKey("userTokenKey", userId);
    }

    private static String buildKey(Object str1, Object... array) {
        StringBuilder stringBuffer = new StringBuilder(PREFIX);
        stringBuffer.append(CACHE_PREFIX).append(str1);
        for (Object obj : array) {
            stringBuffer.append(CACHE_PREFIX).append(obj);
        }
        return stringBuffer.toString();
    }
}
