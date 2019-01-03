package com.manage.dao;

import com.manage.entity.UserAmountDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserAmountDetailMapper {
    int deleteByPrimaryKey(Long delid);

    int insert(UserAmountDetail record);

    int insertSelective(UserAmountDetail record);

    UserAmountDetail selectByPrimaryKey(UserAmountDetail detail);

    int updateByPrimaryKeySelective(UserAmountDetail record);

    int updateByPrimaryKey(UserAmountDetail record);

    List<UserAmountDetail> selectDetail(UserAmountDetail detail);

    Map selectDetailSum(UserAmountDetail detail);

    Double countSalary(@Param("userid") Long userid, @Param("date") String date);
}