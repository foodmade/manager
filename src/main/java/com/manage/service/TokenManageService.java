package com.manage.service;

import com.manage.entity.UserResponse;
import com.manage.entity.Users;

/**
 * token管理中心
 */
public interface TokenManageService {

    /**
     * 创建并缓存token
     * @param user
     */
    String createTokenAndCache(Users user);

    /**
     * 检查token
     * @return
     */
    boolean checkToken(String token);

    /**
     * 获取用户信息
     * @param token
     * @return
     */
    UserResponse getUserBean(String token);

}
