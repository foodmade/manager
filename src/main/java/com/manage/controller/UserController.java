package com.manage.controller;

import com.manage.annotation.BaseChek;
import com.manage.common.Const;
import com.manage.common.model.BaseResult;
import com.manage.entity.UserResponse;
import com.manage.entity.Users;
import com.manage.service.UserService;
import com.manage.vo.QueryUserListRequest;
import com.manage.vo.SingleUserRequest;
import com.manage.vo.UpdateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 添加会员   (管理员使用)
     * @param request  参数
     */
    @RequestMapping("addUser.do")
    @ResponseBody
    @BaseChek(needCheckParameter = true,needCheckToken = true,beanClazz = Users.class,permissionFilter = Const.ADMIN)
    public BaseResult addUser(@RequestAttribute Users request, @RequestAttribute UserResponse userResponse){
        return userService.createUser(request,userResponse);
    }

    /**
     * 添加管理员 (超级管理员使用)
     */
    @RequestMapping("addAdminUser.do")
    @ResponseBody
    @BaseChek(permissionFilter = Const.SUPER_ADMIN,beanClazz = Users.class)
    public BaseResult addAdminUser(@RequestAttribute Users request,@RequestAttribute UserResponse userResponse){
        return userService.addAdminUser(request,userResponse);
    }

    /**
     * 删除会员
     */
    @RequestMapping("deleteUser.do")
    @ResponseBody
    @BaseChek(needCheckToken = true,needCheckParameter = true,beanClazz = Users.class,permissionFilter = Const.SUPER_ADMIN)
    public BaseResult deleteUser(@RequestAttribute Users request, @RequestAttribute UserResponse userResponse){
        return userService.deleteUser(request);
    }

    /**
     * 更新会员信息
     */
    @RequestMapping("updateUser.do")
    @ResponseBody
    @BaseChek(permissionFilter = Const.SUPER_ADMIN,beanClazz = Users.class)
    public BaseResult updateUser(@RequestAttribute Users request){
        return userService.updateUserInfo(request);
    }

    /**
     * 检查用户名是否已经被占用
     */
    @RequestMapping("checkUserName.do")
    @ResponseBody
    @BaseChek(beanClazz = Users.class,permissionFilter = Const.ADMIN)
    public BaseResult checkUserName(@RequestAttribute Users request){
        return userService.checkUserNo(request);
    }

    /**
     * 检查推荐人id是否属于自己的下线
     */
    @RequestMapping("invalidPid.do")
    @ResponseBody
    @BaseChek(beanClazz = Users.class)
    public BaseResult invalidPid(@RequestAttribute Users request){
        return userService.invalidPid(request);
    }

    /**
     * 获取用户信息
     * @param userResponse
     * @return
     */
    @RequestMapping("getUserInfo.do")
    @ResponseBody
    @BaseChek(needCheckToken = true,needCheckParameter = false)
    public BaseResult getDetailUserInfo(@RequestAttribute UserResponse userResponse){
        return userService.getDetailUserInfo(userResponse);
    }

    /**
     * 获取账户余额信息
     * @param userResponse
     * @return
     */
    @RequestMapping("getBalanceInfo.do")
    @ResponseBody
    @BaseChek(needCheckParameter = false)
    public BaseResult getBalanceInfo(@RequestAttribute UserResponse userResponse){
        return userService.getBalanceInfo(userResponse);
    }

    /**
     * 根据用户id获取详细信息 供管理员使用
     * @param request
     * @return
     */
    @RequestMapping("getUserInfoById.do")
    @ResponseBody
    @BaseChek(permissionFilter = Const.ADMIN,beanClazz = Users.class)
    public BaseResult getUserInfoById(@RequestAttribute Users request,@RequestAttribute UserResponse userResponse){
        return userService.getUserInfoById(request,userResponse);
    }

    /**
     * 获取用户列表
     */
    @RequestMapping("getUserList.do")
    @ResponseBody
    @BaseChek(beanClazz = QueryUserListRequest.class)
    public BaseResult getUserList(@RequestAttribute QueryUserListRequest request,@RequestAttribute UserResponse userResponse){
        return userService.getUserList(request,userResponse);
    }

    /**
     * 获取会员结构
     */
    @RequestMapping("getUserTreen.do")
    @ResponseBody
    @BaseChek(beanClazz = Users.class)
    public BaseResult getUserTree(@RequestAttribute Users request,@RequestAttribute UserResponse userResponse){
        return userService.getUserTree(request,userResponse);
    }

    /**
     * 获取会员信息修改列表
     */
    @RequestMapping("getSingleUserInfo.do")
    @ResponseBody
    @BaseChek(beanClazz = SingleUserRequest.class,permissionFilter = Const.SUPER_ADMIN)
    public BaseResult getSingleUserInfo(@RequestAttribute SingleUserRequest request,
                                        @RequestAttribute UserResponse userResponse){
        return userService.getSingleUserInfo(request,userResponse);
    }

    /**
     * 更新当前登录用户信息
     */
    @RequestMapping("updateLoginUser.do")
    @ResponseBody
    @BaseChek(beanClazz = UpdateModel.class)
    public BaseResult updateLoginUser(@RequestAttribute UpdateModel request,
                                      @RequestAttribute UserResponse userResponse){
        return userService.updateLoginUser(request,userResponse);
    }

    /**
     * 密码验证
     */
    @RequestMapping("invalidPassword.do")
    @ResponseBody
    @BaseChek(needCheckToken = true,needCheckParameter = true,beanClazz = UpdateModel.class)
    public BaseResult invalidPassword(@RequestAttribute UpdateModel request,@RequestAttribute UserResponse userResponse){
        return userService.invalidPassword(request,userResponse);
    }

    /**
     * 授权账户
     */
    @RequestMapping("authorityAccount.do")
    @ResponseBody
    @BaseChek(needCheckParameter = true,beanClazz = Users.class,permissionFilter = Const.SUPER_ADMIN)
    public BaseResult authorityAccount(@RequestAttribute Users request){
        return userService.authority(request);
    }

    /**
     * 更新下线信息
     */
    @RequestMapping("updateChildUserInfo.do")
    @ResponseBody
    @BaseChek(beanClazz = Users.class,permissionFilter = Const.SUPER_ADMIN)
    public BaseResult updateChildUserInfo(@RequestAttribute Users request,@RequestAttribute UserResponse userResponse){
        return userService.updateChildUserInfo(request,userResponse);
    }

    /**
     * 检查用户是否满足开放第三区条件
     */
    @RequestMapping("checkMeetTreeArea.do")
    @ResponseBody
    @BaseChek(beanClazz = Users.class)
    public BaseResult checkMeetTreeArea(@RequestAttribute Users request,@RequestAttribute UserResponse userResponse){
        return userService.checkMeetTreeArea(request,userResponse);
    }

    /**
     * 初始化管理员账户
     * @return
     */
    @RequestMapping(value = "initAdminUser.do",method = RequestMethod.POST)
    @ResponseBody
    @BaseChek(needCheckToken = false,needCheckParameter = false)
    public BaseResult initAdminUser(HttpServletRequest request,String initPwd){
        return userService.initSuperAdminUser(initPwd);
    }

    /**
     * 启动刷新会员级别定时任务
     */
    @RequestMapping(value = "executeLevelPush.do",method = RequestMethod.POST)
    @ResponseBody
    @BaseChek(permissionFilter = Const.SUPER_ADMIN,needCheckParameter = false)
    public BaseResult executeLevelPush(HttpServletRequest request,String pwd){
        return userService.updateUserLevel(pwd);
    }
}
