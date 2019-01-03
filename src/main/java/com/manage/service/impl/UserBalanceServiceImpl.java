package com.manage.service.impl;

import com.manage.common.commonUtil.DateUtil;
import com.manage.common.enums.UserLevelEnum;
import com.manage.dao.UserBalanceMapper;
import com.manage.entity.UserBalance;
import com.manage.entity.Users;
import com.manage.service.UserBalanceService;
import com.manage.service.UserBankcordsService;
import com.manage.service.UserService;
import com.manage.vo.UserScope;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserBalanceServiceImpl implements UserBalanceService {

    private static Logger logger = Logger.getLogger(UserBalanceServiceImpl.class);

    @Autowired
    private UserBalanceMapper mapper;
    @Autowired
    private UserService userService;
    @Override
    public int deleteByPrimaryKey(Long balid) {
        return mapper.deleteByPrimaryKey(balid);
    }

    @Override
    public int insert(UserBalance record) {
        return mapper.insert(record);
    }

    @Override
    public int insertSelective(UserBalance record) {
        return mapper.insertSelective(record);
    }

    @Override
    public UserBalance selectByPrimaryKey(UserBalance balance) {
        return mapper.selectByPrimaryKey(balance);
    }

    @Override
    public int updateByPrimaryKeySelective(UserBalance record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserBalance record) {
        return mapper.updateByPrimaryKey(record);
    }

    @Override
    public Map getDateForMonth(String month) {
        Map m = new HashMap<>();
        String[] split = month.split("-");
        if(split.length!=2){
            throw new RuntimeException("传入参数格式有误");
        }
        Date endDate = null;
        Date beginDate = null;
        try{
            endDate = DateUtil.getMonthDate(new Integer(split[0]), new Integer(split[1]), 0);
            beginDate = DateUtil.getMonthDate(new Integer(split[0]), new Integer(split[1]), 1);
        }catch (Exception e){
            throw new RuntimeException("传入参数格式有误");
        }
        // 收入
        UserScope scope = new UserScope();
        scope.setBeginDate(beginDate);
        scope.setEndDate(endDate);
        // 新用户才被统计
		scope.setState(0);
		Long start = System.currentTimeMillis();
        int size = userService.qryUserList(scope).size();
        Long start1 = System.currentTimeMillis();
        logger.debug("【第一阶段耗时:】"+(start1-start));
        m.put("inAmount", new BigDecimal(size * 2980));
        // 支出
        scope.setEndDate(null);
        scope.setBeginDate(null);
        // 当前非新增的会员列表
        Integer[] lels = new Integer[]{
                UserLevelEnum.LEVLE2.getLevelCode(),
                UserLevelEnum.LEVLE3.getLevelCode(),
                UserLevelEnum.LEVLE4.getLevelCode(),
                UserLevelEnum.LEVLE5.getLevelCode(),
                UserLevelEnum.LEVLE6.getLevelCode(),
                UserLevelEnum.LEVLE7.getLevelCode()
        };
        scope.setUserLevls(lels);
        List<Users> users = userService.qryUserList(scope);
        // 此处是通过分别查询每一个用户对于时间区间的工资明细之和来计算，但是经过测试，发现与数据库交互太多，导致性能缓慢。240Users-12月  查询数据大约12000ms
        /*BigDecimal re = new BigDecimal(0);
        BigDecimal re = new BigDecimal(0);
        Long start2 = System.currentTimeMillis();
        logger.debug("【第二阶段耗时：】"+(start2-start1));
        BigDecimal re = new BigDecimal(0);
        for (int i = 0; i < users.size(); i++) {
            BigDecimal amount = new BigDecimal(userService.getAmount(users.get(i), beginDate, endDate));
            re = re.add(amount);
        }*/
        // 换一种实现思路，通过数据库，直接sum出总计金额。一次数据库交互，且减少数据返回数据量，提高性能。同上数据，2145ms。问题解决
        Map map = new HashMap();
        if(users == null || users.size()==0){
            users = null;
        }
        map.put("users",users);
        map.put("beginDate",beginDate);
        map.put("endDate",endDate);
        BigDecimal re = mapper.qrySumAmount(map);
        re = re==null?new BigDecimal(0.00):re;
        //logger.debug("【第三阶段耗时：】"+(System.currentTimeMillis()-start2));
        m.put("date", month);
        m.put("outAmount", re.doubleValue());
        m.put("netWorthAmount", new BigDecimal(size * 2980).subtract(re));
        return m;
    }

    @Override
    public Map getDateForMonthDetail(String month) {
        String[] split = month.split("-");
        if(split.length!=2){
            throw new RuntimeException("传入参数格式有误");
        }
        Date endDate = null;
        Date beginDate = null;
        try{
            endDate = DateUtil.getMonthDate(new Integer(split[0]), new Integer(split[1]), 0);
            beginDate = DateUtil.getMonthDate(new Integer(split[0]), new Integer(split[1]), 1);
        }catch (Exception e){
            throw new RuntimeException("传入参数格式有误");
        }
        UserScope scope = new UserScope();
        // 当前非新增的会员列表
        Integer[] lels = new Integer[]{
                UserLevelEnum.LEVLE2.getLevelCode(),
                UserLevelEnum.LEVLE3.getLevelCode(),
                UserLevelEnum.LEVLE4.getLevelCode(),
                UserLevelEnum.LEVLE5.getLevelCode(),
                UserLevelEnum.LEVLE6.getLevelCode(),
                UserLevelEnum.LEVLE7.getLevelCode()
        };
        scope.setUserLevls(lels);
        scope.setState(0);
        List<Users> users = userService.qryUserList(scope);
        BigDecimal re = new BigDecimal(0);
        for (int i = 0; i < users.size(); i++) {
            BigDecimal amount = new BigDecimal(userService.getAmount(users.get(i), beginDate, endDate).toString());
            users.get(i).setMonthAmount(amount.doubleValue());
            re = re.add(amount);
        }
        Map map = new HashMap();
        map.put("data",users);
        map.put("size",users.size());
        map.put("sumAmount",re);
        return map;
    }
}
