package com.manage.controller;

import com.manage.annotation.BaseChek;
import com.manage.common.Const;
import com.manage.common.enums.PermissionEnum;
import com.manage.common.model.BaseResult;
import com.manage.entity.UserResponse;
import com.manage.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 首页管理
 */
@Controller
public class MainController {

    @Autowired
    private MainService mainService;

    /**
     * 获取用户基本信息
     */
    @RequestMapping(value = "getBackUserInfo.do",method = RequestMethod.POST)
    @ResponseBody
    @BaseChek(needCheckParameter = false,permissionFilter = Const.SUPER_ADMIN)
    public BaseResult getUserInfo(@RequestAttribute UserResponse userResponse){
        return new BaseResult(mainService.getUserInfo(userResponse));
    }

}
