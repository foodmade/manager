package com.manage.dao;

import com.manage.entity.UserLevel;

import java.util.List;

public interface UserLevelMapper {

    int deleteByPrimaryKey(Long levelid);

    int insert(UserLevel record);

    int insertSelective(UserLevel record);

    UserLevel selectByPrimaryKey(Long levelid);

    int updateByPrimaryKeySelective(UserLevel record);

    int updateByPrimaryKey(UserLevel record);

    List<UserLevel> selectAll();
}