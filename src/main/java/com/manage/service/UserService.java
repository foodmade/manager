package com.manage.service;

import com.manage.common.model.BaseResult;
import com.manage.entity.UserResponse;
import com.manage.entity.Users;
import com.manage.vo.QueryUserListRequest;
import com.manage.vo.SingleUserRequest;
import com.manage.vo.UpdateModel;
import com.manage.vo.UserScope;

import java.util.Date;
import java.util.List;

public interface UserService {

    /**
     * 注册会员
     * @param users
     * @return
     */
    BaseResult createUser(Users users, UserResponse response);

    /**
     * 查询用户  用户名为查询条件
     * @param userName
     * @return
     */
    Users queryUserByUserName(String userName);

    /**
     * 推荐id是否存在
     * @param pid
     * @return
     */
    Users queryUserByPid(Long pid);

    /**
     * 根据查询条件查询用户
     * @param scope
     * @return
     */
    List<Users> qryUserList(UserScope scope);

    /**
     * 删除用户 逻辑删除
     * @param users
     * @return
     */
    BaseResult deleteUser(Users users);

    /**
     * 更新用户信息
     * @param request
     */
    BaseResult updateUserInfo(Users request);

    /**
     * 检查用户名是否存在
     * @param request
     * @return
     */
    BaseResult checkUserNo(Users request);

    /**
     * 验证接点人
     * @param request
     * @return
     */
    BaseResult invalidPid(Users request);

    /**
     * 获取用户详细信息 根据userId
     * @param userResponse
     * @return
     */
    BaseResult getDetailUserInfo(UserResponse userResponse);

    /**
     * 获取用户账户金额信息
     * @param userResponse
     * @return
     */
    BaseResult getBalanceInfo(UserResponse userResponse);

    /**
     * 根据用户id获取用户详情信息
     * @param request
     * @return
     */
    BaseResult getUserInfoById(Users request,UserResponse userResponse);

    /**
     * 获取用户列表
     * @param request
     * @param userResponse
     * @return
     */
    BaseResult getUserList(QueryUserListRequest request, UserResponse userResponse);

    /**
     * 获取会员结构
     * @param request
     * @param userResponse
     * @return
     */
    BaseResult getUserTree(Users request, UserResponse userResponse);

    /**
     * 获取会员信息修改列表
     * @param request
     * @return
     */
    BaseResult getSingleUserInfo(SingleUserRequest request,UserResponse userResponse);

    /**
     * 更新当前登录用户信息
     * @param request
     * @param userResponse
     * @return
     */
    BaseResult updateLoginUser(UpdateModel request, UserResponse userResponse);

    /**
     * 密码验证
     * @param request
     * @param userResponse
     * @return
     */
    BaseResult invalidPassword(UpdateModel request, UserResponse userResponse);

    /**
     *  查询用户在指定时间区域内的工资累计
     * @param user
     * @param begin
     * @param end
     * @return
     */
    public Double getAmount(Users user , Date begin, Date end);

    /**
     * 授权用户
     * @param users
     * @return
     */
    BaseResult authority(Users users);

    /**
     * 添加管理员账号
     */
    BaseResult addAdminUser(Users request,UserResponse userResponse);

    /**
     * 更新下线用户信息
     */
    BaseResult updateChildUserInfo(Users request, UserResponse userResponse);

    /**
     * 检查用户是否满足开放第三区条件
     */
    BaseResult checkMeetTreeArea(Users users, UserResponse userResponse);

    /**
     * 初始化超级管理员
     * @param initPwd
     * @return
     */
    BaseResult initSuperAdminUser(String initPwd);

    BaseResult updateUserLevel(String pwd);

}
