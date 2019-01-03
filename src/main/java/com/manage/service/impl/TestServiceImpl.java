package com.manage.service.impl;

import com.manage.common.redisUtil.RedisClient;
import com.manage.dao.UsersMapper;
import com.manage.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private UsersMapper userMapper;

    @Autowired
    private RedisClient redisClient;
    @Override
    public Object getUserInfo(Long userId) {
        return userMapper.selectByPrimaryKey(1L);
    }
}
