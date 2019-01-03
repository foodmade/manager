package com.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.manage.common.redisUtil.RedisClient;
import com.manage.entity.UserResponse;
import com.manage.service.MainService;
import com.manage.service.TokenManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainServiceImpl implements MainService {

    @Autowired
    private TokenManageService tokenManageService;

    @Autowired
    private RedisClient redisClient;

    @Override
    public Object getUserInfo(UserResponse user) {
        return JSON.toJSONString(user);
    }
}
