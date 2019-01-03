package com.manage.service;

import com.manage.entity.UserBalance;

import java.util.List;
import java.util.Map;

public interface UserBalanceService {

    int deleteByPrimaryKey(Long balid);

    int insert(com.manage.entity.UserBalance record);

    int insertSelective(com.manage.entity.UserBalance record);

    UserBalance selectByPrimaryKey(UserBalance balance);

    int updateByPrimaryKeySelective(com.manage.entity.UserBalance record);

    int updateByPrimaryKey(com.manage.entity.UserBalance record);

    public Map getDateForMonth(String month);

    Map getDateForMonthDetail(String date);
}
