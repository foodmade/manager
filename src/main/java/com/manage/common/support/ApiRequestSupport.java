package com.manage.common.support;

import com.alibaba.fastjson.JSON;
import com.manage.entity.JsonEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ApiRequestSupport {

    private static final Logger logger = LoggerFactory.getLogger(ApiRequestSupport.class);

    public static void invokeExceptionWrapper(HttpServletResponse response, String code, String message)
            throws IOException {
        JsonEntity json = new JsonEntity();
        json.setCode(code);
        json.setMessage(message);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(json));
    }
}
