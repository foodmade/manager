package com.manage.service;

import com.manage.common.model.BaseResult;
import com.manage.entity.UserResponse;
import com.manage.vo.LoginParams;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoginService {

    BaseResult login(LoginParams loginParams,HttpServletRequest request);

    Boolean validateCode(HttpServletRequest request, HttpServletResponse response);

    BaseResult logout(UserResponse userResponse,HttpServletRequest request);

    BaseResult validateCodeBase64(HttpServletRequest request);
}
