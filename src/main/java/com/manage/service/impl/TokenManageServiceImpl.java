package com.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.manage.common.commonUtil.JedisKey;
import com.manage.common.redisUtil.RedisClient;
import com.manage.entity.UserResponse;
import com.manage.entity.Users;
import com.manage.service.TokenManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.UUID;

@Service
public class TokenManageServiceImpl implements TokenManageService {

    @Autowired
    private RedisClient redisClient;

    @Override
    public String createTokenAndCache(Users user) {
        // 使用uuid作为源token
        String token = UUID.randomUUID().toString().replace("-", "");
        user.setPwd("");
        Jedis jedis = redisClient.getJedis(0);
        try {
            //token缓存到redis 实现简易版session共享  token有效期2天
            jedis.setex(token, (JedisKey.ONE_DAY * 2),JSON.toJSONString(user));
        } finally {
            redisClient.release(jedis,false);
        }
        return token;
    }

    @Override
    public boolean checkToken(String token) {

        if(StringUtils.isEmpty(token)){
            return false;
        }
        Jedis jedis = redisClient.getJedis(0);
        String cacheUser = null;
        try {
            cacheUser = jedis.get(token);
            if(cacheUser != null){
                //说明当前token有效 延长过期时间
                jedis.setex(token,(JedisKey.ONE_DAY * 2),cacheUser);
                return true;
            }
        } catch (Exception e) {
            redisClient.release(jedis,false);
        }
        return false;
    }

    @Override
    public UserResponse getUserBean(String token) {
        if(StringUtils.isEmpty(token)){
            return null;
        }
        String cacheUser = redisClient.get(token);
        if(cacheUser == null){
            return null;
        }

        Users user = JSON.parseObject(cacheUser,Users.class);
        return getUserResponseModel(user,token);
    }

    private UserResponse getUserResponseModel(Users user,String token) {
        UserResponse response = new UserResponse();

        response.setRegisterTime(user.getRegisttime());
        response.setRole(user.getPerid());
        response.setUserId(user.getUserid());
        response.setUserNo(user.getUserno());
        response.setToken(token);
        response.setLevel(user.getLevelid());
        response.setUsername(user.getUsername());
        response.setToppid(user.getToppid());
        return response;
    }
}
