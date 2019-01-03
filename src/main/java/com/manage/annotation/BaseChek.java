package com.manage.annotation;

import com.manage.common.Const;
import com.manage.common.enums.PermissionEnum;
import com.manage.entity.UserResponse;

import java.lang.annotation.*;

/**
 * 请求进入系统的前置检查
 * 1 needLogin (是否需要登录权限)
 * 2 wait add....
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BaseChek {

    /**
     * 是否需要登录
     *
     * @return true需要 false不需要
     */
    boolean needLogin() default true;

    /**
     * 入参检查bean class
     */
    Class<?> beanClazz() default UserResponse.class;

    /**
     * 是否需要检查参数
     */
    boolean needCheckParameter() default true;

    /**
     * 是否需要验证token
     */
    boolean needCheckToken() default true;

    /**
     * 权限拦截  默认员工级别
     */
    String permissionFilter() default Const.EMPLOYEE;
}
