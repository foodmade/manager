package com.manage.service.impl;

import com.manage.common.AreaUtil.IPAddressUtils;
import com.manage.common.commonUtil.CommonUtil;
import com.manage.common.commonUtil.DateUtils;
import com.manage.common.commonUtil.JedisKey;
import com.manage.common.commonUtil.ValidateCode;
import com.manage.common.enums.ExceptionEnum;
import com.manage.common.model.BaseResult;
import com.manage.common.redisUtil.RedisClient;
import com.manage.dao.LoginLogMapper;
import com.manage.dao.UsersMapper;
import com.manage.entity.LoginLog;
import com.manage.entity.UserResponse;
import com.manage.entity.Users;
import com.manage.service.LoginService;
import com.manage.service.TokenManageService;
import com.manage.vo.LoginParams;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Service
public class LoginServiceImpl implements LoginService {

    private static Logger logger = Logger.getLogger(LoginService.class);

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private TokenManageService tokenManageService;

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    public BaseResult login(LoginParams loginParams,HttpServletRequest request) {

        if(!frontCheckParam(loginParams)){
            return new BaseResult(ExceptionEnum.PARAMEMPTYPEROR);
        }

        String redisKey = JedisKey.vatcodeKey(request.getSession().getId());
        logger.info("获取验证码时候的sessionId:"+request.getSession().getId());
        //校验验证码
        String baseCode = redisClient.get(redisKey);
        //无论验证成功与否 验证码只允许使用一次
        redisClient.del(redisKey);
        if("adminTest".equals(loginParams.getCode())){
            logger.debug("【测试人员登录,跳过验证码】");
        }else if(commonUtil.strIsNULL(baseCode)
                || !(baseCode.toLowerCase().equals(loginParams.getCode()))){
            return new BaseResult(ExceptionEnum.INVALIDCODE);
        }

        Users user;
        try {
            user = usersMapper.selectByUserNo(loginParams.getUserName());
        } catch (Exception e) {
            logger.error("query userName fail e:"+e.getMessage());
            return new BaseResult(ExceptionEnum.DATABASEERROR);
        }
        if(user == null){
            return BaseResult.makeExceptionResult("10002","用户名不存在");
        }
        //开始进行密码校验
        if(!loginParams.getPassword().equals(user.getPwd())){
            return new BaseResult(ExceptionEnum.PASSWORDINVALID);
        }
        //记录登陆日志
        recordLog(user,request.getRemoteAddr());
        //缓存用户信息至redis缓存
        String token = tokenManageService.createTokenAndCache(user);
        return new BaseResult(token);
    }

    private void recordLog(Users user, String remoteAddr) {
        String area = getCity(remoteAddr);
        LoginLog loginLog = new LoginLog();
        loginLog.setAddress(area);
        loginLog.setIp(remoteAddr);
        loginLog.setUserid(user.getUserid());
        loginLog.setLogintime(DateUtils.formatDate(new Date(),DateUtils.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));
        try {
            loginLogMapper.insert(loginLog);
        } catch (Exception e) {
            logger.error("【登录日志插入失败 失败原因:】"+e.getMessage());
        }
    }

    private String getCity(String ip){
        //根据ip获取地址
        IPAddressUtils ipAddressUtils = new IPAddressUtils();
        ipAddressUtils.init();
        String country = ipAddressUtils.getIPLocation(ip).getCountry();
        String city = ipAddressUtils.getIPLocation(ip).getCity();
        return country+","+city;
    }

    @Override
    public Boolean validateCode(HttpServletRequest request, HttpServletResponse response) {
        return true;
    }

    @Override
    public BaseResult logout(UserResponse userResponse,HttpServletRequest request) {
        if(userResponse == null){
            return new BaseResult(ExceptionEnum.TOKENRERROR);
        }
        if(!doInvalidateSession(request,userResponse)){
            return new BaseResult(ExceptionEnum.SERVER_ERR);
        }
        return new BaseResult(ExceptionEnum.SUCCESS);
    }

    @Override
    public BaseResult validateCodeBase64(HttpServletRequest request) {
        String key = request.getSession().getId();

        ValidateCode.Validate vCode;
        try {
            logger.info("生成验证码sessionId:"+key);
            vCode = ValidateCode.getRandomCode();
        } catch (Exception e) {
            logger.error("生成验证码失败 e:"+e.getMessage());
            return new BaseResult(ExceptionEnum.VALIDATECODEERR);
        }

        //将验证码写入redis缓存 并设置超时时间  超时时间为3分钟
        redisClient.setex(JedisKey.vatcodeKey(key),vCode.getValue(),JedisKey.THREE_MINUTE);
        return new BaseResult(vCode.getBase64Str());
    }

    private Boolean doInvalidateSession(HttpServletRequest request,UserResponse userResponse){
        Jedis jedis = redisClient.getJedis(JedisKey.TOKENSET);
        try {
            request.getSession().invalidate();
            jedis.del(userResponse.getToken());
        } catch (Exception e) {
            logger.error("【注销登陆失败 userId:"+userResponse.getUserId()+"】 e:"+e.getMessage());
            redisClient.release(jedis,false);
            return false;
        }
        return true;
    }

    private boolean frontCheckParam(LoginParams loginParams) {
        return loginParams != null && !loginParams.invalid();
    }
}
