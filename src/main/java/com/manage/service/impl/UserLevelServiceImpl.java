package com.manage.service.impl;

import com.manage.dao.UserLevelMapper;
import com.manage.entity.UserLevel;
import com.manage.service.UserLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLevelServiceImpl implements UserLevelService {
    @Autowired
    private UserLevelMapper levelMapper;
    @Override
    public int deleteByPrimaryKey(Long levelid) {
        return levelMapper.deleteByPrimaryKey(levelid);
    }

    @Override
    public int insert(UserLevel record) {
        return levelMapper.insert(record);
    }

    @Override
    public int insertSelective(UserLevel record) {
        return levelMapper.insertSelective(record);
    }

    @Override
    public UserLevel selectByPrimaryKey(Long levelid) {
        return levelMapper.selectByPrimaryKey(levelid);
    }

    @Override
    public int updateByPrimaryKeySelective(UserLevel record) {
        return levelMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserLevel record) {
        return levelMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<UserLevel> selectAll() {
        return levelMapper.selectAll();
    }
}
