package com.manage.service.impl;

import com.manage.dao.UserBalanceLogMapper;
import com.manage.entity.UserBalanceLog;
import com.manage.service.UserBalanceLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBalanceLogServiceImpl implements UserBalanceLogService {
    @Autowired
    private UserBalanceLogMapper mapper;
    @Override
    public int deleteByPrimaryKey(Long logid) {
        return mapper.deleteByPrimaryKey(logid);
    }

    @Override
    public int insert(UserBalanceLog record) {
        return mapper.insert(record);
    }

    @Override
    public int insertSelective(UserBalanceLog record) {
        return mapper.insertSelective(record);
    }

    @Override
    public UserBalanceLog selectByPrimaryKey(Long logid) {
        return mapper.selectByPrimaryKey(logid);
    }

    @Override
    public int updateByPrimaryKeySelective(UserBalanceLog record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserBalanceLog record) {
        return mapper.updateByPrimaryKey(record);
    }
}
