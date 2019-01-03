package com.manage.dao;

import com.manage.entity.UserBalance;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Map;

public interface UserBalanceMapper {
    int deleteByPrimaryKey(Long balid);

    int insert(UserBalance record);

    int insertSelective(UserBalance record);

    UserBalance selectByPrimaryKey(UserBalance balance);

    int updateByPrimaryKeySelective(UserBalance record);

    int updateByPrimaryKey(UserBalance record);

    UserBalance selectByUserId(@Param("userid") Long userId);

    BigDecimal qrySumAmount(Map map);
}