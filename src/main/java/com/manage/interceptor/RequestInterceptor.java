package com.manage.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.manage.annotation.BaseChek;
import com.manage.common.Const;
import com.manage.common.commonUtil.Validator;
import com.manage.common.enums.ExceptionEnum;
import com.manage.common.exception.TokenException;
import com.manage.common.support.ApiRequestSupport;
import com.manage.common.support.HttpSupport;
import com.manage.entity.UserResponse;
import com.manage.service.TokenManageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * 参数校验
 */
public class RequestInterceptor extends HandlerInterceptorAdapter {
    private static Logger logger = Logger.getLogger(RequestInterceptor.class);

    @Autowired
    private TokenManageService tokenManageService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("RequestInterceptor..........");
        if (handler instanceof HandlerMethod) {
            try {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                Method method = handlerMethod.getMethod();
                BaseChek annotation = method.getAnnotation(BaseChek.class);

                if (annotation == null) {
                    return true;
                }
                String url = request.getRequestURI();
                //获取头信息
                HashMap<String, String> headers = HttpSupport.getHeader(request);
                //获取token
                String token = request.getParameter("token");
//                String token = headers.get("token");
                // 参数字符串
                String jsonString = request.getParameter("data");

                boolean needLogin = annotation.needLogin();
                boolean needCheckToken = annotation.needCheckToken();
                logger.info("访问url：" + url);
                logger.info("method=" + request.getMethod() + ",needLogin=" + needLogin + ", needCheckToken:" + needCheckToken + " beanClass:" + annotation.beanClazz().getSimpleName());

                if (needCheckToken && StringUtils.isEmpty(token)) {
                    logger.info("防问： {" + url + "} token为空 data:" + jsonString);
                    throw new TokenException(ExceptionEnum.TOKENRERROR);
                }

                if (needCheckToken) {
                    //验证当前token有效性
                    if (!tokenManageService.checkToken(token)) {
                        throw new TokenException(ExceptionEnum.TOKENRERROR);
                    } else {
                        UserResponse user = tokenManageService.getUserBean(token);
                        if (user == null) {
                            throw new TokenException(ExceptionEnum.TOKENRERROR);
                        } else {
                            //设置登陆ip
                            user.setRequestIp(request.getRemoteAddr());
                            request.setAttribute(Const.ACCESSTOKENKEY, user);
                        }
                    }
                }

                //参数实例化
                if (annotation.needCheckParameter()) {
                    Object model;
                    try {
                        model = getModel(jsonString, annotation.beanClazz());
                    } catch (Exception e) {
                        throw new InstantiationException(e.getMessage());
                    }
                    request.setAttribute("request", model);
                }
            } catch (TokenException e) {
                logger.error("【tokenException 拦截器发生异常：】"+e.getMessage());
                ApiRequestSupport.invokeExceptionWrapper(response, e.getCode(), e.getMessage());
                return false;
            } catch (InstantiationException e) {
                logger.error("【InstantiationException 拦截器发生异常：】"+e.getMessage());
                ApiRequestSupport.invokeExceptionWrapper(response, ExceptionEnum.SERVER_ERR.getCode(), e.getMessage());
                return false;
            } catch (Exception e) {
                logger.error("【Exception 拦截器发生异常：】"+e.getMessage());
                ApiRequestSupport.invokeExceptionWrapper(response, ExceptionEnum.SERVER_ERR.getCode(),
                        ExceptionEnum.SERVER_ERR.getMessage());
                return false;
            }
        }
        return true;
    }

    private <T> T getModel(String json, Class<T> clazz) throws Exception {
        Validator.validate(JSON.parseObject(json, clazz));
        return JSON.parseObject(json, clazz);
    }
}
