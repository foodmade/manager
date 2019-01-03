package com.manage.service.impl;

import com.manage.dao.UserAmountDetailMapper;
import com.manage.entity.UserAmountDetail;
import com.manage.service.UserAmountDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserAmountDetailServiceImpl implements UserAmountDetailService {

    @Autowired
    private UserAmountDetailMapper mapper;

    public int deleteByPrimaryKey(Long delid){
        return mapper.deleteByPrimaryKey(delid);
    }

    public int insert(UserAmountDetail record){
        return mapper.insert(record);
    }

    public int insertSelective(UserAmountDetail record){
        return mapper.insertSelective(record);
}

    public UserAmountDetail selectByPrimaryKey(UserAmountDetail delid){
        return mapper.selectByPrimaryKey(delid);
    }

    public List<UserAmountDetail> selectDetail(UserAmountDetail detail){
        return mapper.selectDetail(detail);
    }

    @Override
    public Map selectDetailSum(UserAmountDetail detail) {
        return mapper.selectDetailSum(detail);
    }

    public int updateByPrimaryKeySelective(UserAmountDetail record){
        return mapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(UserAmountDetail record){
        return mapper.updateByPrimaryKey(record);
    }
}
