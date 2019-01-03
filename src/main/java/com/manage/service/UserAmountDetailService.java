package com.manage.service;

import com.manage.entity.UserAmountDetail;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface UserAmountDetailService {
    int deleteByPrimaryKey(Long delid);

    int insert(UserAmountDetail record);

    int insertSelective(UserAmountDetail record);

    UserAmountDetail selectByPrimaryKey(UserAmountDetail delid);

    List<UserAmountDetail> selectDetail(UserAmountDetail detail);

    Map selectDetailSum(UserAmountDetail detail);

    int updateByPrimaryKeySelective(UserAmountDetail record);

    int updateByPrimaryKey(UserAmountDetail record);
}
