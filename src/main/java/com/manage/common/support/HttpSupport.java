package com.manage.common.support;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;

public class HttpSupport {

    /**
     * 获取http首部
     *
     * @param request
     * @return
     */

    public static HashMap<String, String> getHeader(HttpServletRequest request) {
        HashMap<String, String> map = new HashMap<String, String>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String element = headerNames.nextElement();
            String value = request.getHeader(element);
            map.put(element.toLowerCase(), value);
        }
        return map;
    }
}
