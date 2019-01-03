package com.manage.controller;

import com.manage.annotation.BaseChek;
import com.manage.common.enums.ExceptionEnum;
import com.manage.common.exception.TokenException;
import com.manage.common.model.BaseResult;
import com.manage.entity.UserResponse;
import com.manage.service.LoginService;
import com.manage.vo.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 用户登陆
     * @param request  参数
     */
    @RequestMapping(value = "/login.do",method = RequestMethod.POST)
    @ResponseBody
    @BaseChek(needCheckToken = false,needCheckParameter = true,beanClazz = LoginParams.class)
    public BaseResult login(@RequestAttribute LoginParams request,HttpServletRequest requestes){
        return loginService.login(request,requestes);
    }

    /**
     * 流式响应
     * 获取验证码
     */
    @RequestMapping(value="/validateCode.do",method = RequestMethod.GET)
    @BaseChek(needCheckToken = false,needCheckParameter = false)
    public void validateCode(HttpServletRequest request, HttpServletResponse response){
        loginService.validateCode(request, response);
    }

    /**
     * 返回base64编码
     * 获取验证码
     */
    @RequestMapping(value = "/validateCodeBase64.do",method = RequestMethod.GET)
    @ResponseBody
    @BaseChek(needCheckToken = false,needCheckParameter = false)
    public BaseResult validateCodeBase64(HttpServletRequest request){
        return loginService.validateCodeBase64(request);
    }

    /**
     * 退出登录
     */
    @RequestMapping("logout.do")
    @ResponseBody
    @BaseChek(needCheckParameter = false,needCheckToken = true)
    public BaseResult logout(@RequestAttribute UserResponse userResponse,HttpServletRequest request,HttpServletResponse response){
        BaseResult result = loginService.logout(userResponse,request);
        if(!result.getCode().equals("200")){
            return new BaseResult(ExceptionEnum.SERVER_ERR);
        }
        return new BaseResult(ExceptionEnum.SUCCESS);
    }

}
