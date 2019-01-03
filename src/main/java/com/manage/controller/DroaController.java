package com.manage.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.manage.annotation.BaseChek;
import com.manage.common.Const;
import com.manage.common.enums.ExceptionEnum;
import com.manage.common.model.BaseResult;
import com.manage.dao.UserBankcordsMapper;
import com.manage.entity.*;
import com.manage.service.*;
import com.manage.vo.DroaApply;
import com.manage.vo.DroaScope;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @Description 提现前端处理类
 *
 * @author chenlu
 *
 * @since 2018-7-22
 */

@Controller
public class DroaController {

    @Autowired
    private DroaService droaService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserBalanceService balanceService;
    @Autowired
    private UserBalanceLogService logService;
    @Autowired
    private UserBankcordsService bankcordsService;
    /**
     * @Description 查询提现记录
     *
     * @author chenlu
     *
     * @since 2018-7-22
      */
    @RequestMapping(value="/qryDroaList.do",method = RequestMethod.POST)
    @BaseChek(needCheckToken = false,beanClazz = DroaScope.class,permissionFilter = Const.SUPER_ADMIN)
    @ResponseBody
    public BaseResult qryDroaList(@RequestAttribute DroaScope request){
        Map result = new HashMap();
        result.put("data",droaService.qryDroaList(request));
        result.put("page",droaService.qryDroaListCount(request));
        BaseResult re = new BaseResult(result);
        return re;
    }

    /**
     * @Description 提现申请
     *
     * @author chenlu
     *
     * @since 2018-7-22
     */
    @RequestMapping(value="/doDroa.do",method = RequestMethod.POST)
    @BaseChek(needCheckToken = false,beanClazz = DroaApply.class)
    @ResponseBody
    public BaseResult doDroa(@RequestAttribute DroaApply request/*, @RequestAttribute UserResponse userResponse*/){
        BaseResult result = new BaseResult();
        if(request==null || request.getUserid()==null || request.getDroaamount()==null){
            result.setCode(ExceptionEnum.PARAMEMPTYPEROR.getCode());
            result.setMessage(ExceptionEnum.PARAMEMPTYPEROR.getMessage());
            return result;
        }
        Users user = userService.queryUserByPid(request.getUserid());
        // 校验支付密码
        if(request.getPaypwd()==null || !request.getPaypwd().equals(user.getPaypwd())){
            result.setCode(ExceptionEnum.PASSWORDINVALID.getCode());
            result.setMessage(ExceptionEnum.PASSWORDINVALID.getMessage());
            return result;
        }
        /**
         * 提现申请日期只能每月25日；提心金额只能500的倍数
         */
        Calendar cal = Calendar.getInstance();
        int  day = cal.get(cal.DATE);
        if(day != 25){
            result.setCode(ExceptionEnum.DAYNUMERROR.getCode());
            result.setMessage(ExceptionEnum.DAYNUMERROR.getMessage());
            return result;
        }
        if (request.getDroaamount() % 500 !=0){
            result.setCode(ExceptionEnum.AMOUNTERROR.getCode());
            result.setMessage(ExceptionEnum.AMOUNTERROR.getMessage());
            return result;
        }
        // 更新余额信息
        UserBalance s = new UserBalance();
        s.setUserid(user.getUserid());
        UserBalance balance = balanceService.selectByPrimaryKey(s);
        if(balance.getBalance() < request.getDroaamount()){
            result.setCode(ExceptionEnum.BALANCEERROR.getCode());
            result.setMessage(ExceptionEnum.BALANCEERROR.getMessage());
            return result;
        }
        //保存提现申请
        request.setCrod(user.getIdcord());
        request.setBalance(balance.getBalance().doubleValue()-request.getDroaamount().doubleValue());
        droaService.doDroa(request);

        UserBalanceLog log = new UserBalanceLog();
        log.setOldbalance(balance.getBalance());
        balance.setBalance(balance.getBalance()-request.getDroaamount());
        balanceService.updateByPrimaryKey(balance);

        // 记录余额变更日志
        log.setBalid(balance.getBalid());
        log.setAmount(request.getDroaamount());
        log.setType(2);
        log.setOptiontype(2);
        logService.insert(log);

        request.setCrod(ExceptionEnum.SUCCESS.getCode());
        result.setMessage(ExceptionEnum.SUCCESS.getMessage());
        return result;
    }

    /**
     * @Description 提现处理
     *
     * @author chenlu
     *
     * @since 2018-7-22
     */
    @RequestMapping(value="/handleDroa.do",method = RequestMethod.POST)
    @BaseChek(needCheckToken = false,beanClazz = Droa.class,permissionFilter = Const.SUPER_ADMIN)
    @ResponseBody
    public BaseResult handleDroa(@RequestAttribute Droa request){
        droaService.handleDroa(request);
        return BaseResult.success(null);
    }
}
