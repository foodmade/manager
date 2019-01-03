package com.manage.service.impl;

import com.manage.dao.UserBankcordsMapper;
import com.manage.entity.UserBankcords;
import com.manage.service.UserBankcordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBankcordsServiceImpl implements UserBankcordsService {
    @Autowired
    private UserBankcordsMapper bankcordsMapper;
    @Override
    public int deleteByPrimaryKey(Long cardid) {
        return bankcordsMapper.deleteByPrimaryKey(cardid);
    }

    @Override
    public int insert(UserBankcords record) {
        return bankcordsMapper.insert(record);
    }

    @Override
    public int insertSelective(UserBankcords record) {
        return bankcordsMapper.insertSelective(record);
    }

    @Override
    public UserBankcords selectByPrimaryKey(Long cardid) {
        return bankcordsMapper.selectByPrimaryKey(cardid);
    }

    @Override
    public int updateByPrimaryKeySelective(UserBankcords record) {
        return bankcordsMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserBankcords record) {
        return bankcordsMapper.updateByPrimaryKey(record);
    }
}
