package com.manage.service;

import com.manage.entity.UserBalanceLog;

public interface UserBalanceLogService {
    int deleteByPrimaryKey(Long logid);

    int insert(UserBalanceLog record);

    int insertSelective(UserBalanceLog record);

    UserBalanceLog selectByPrimaryKey(Long logid);

    int updateByPrimaryKeySelective(UserBalanceLog record);

    int updateByPrimaryKey(UserBalanceLog record);
}
