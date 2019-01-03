package com.manage.dao;

import com.manage.entity.UserPermision;

public interface UserPermisionMapper {
    int deleteByPrimaryKey(Long perid);

    int insert(UserPermision record);

    int insertSelective(UserPermision record);

    UserPermision selectByPrimaryKey(Long perid);

    int updateByPrimaryKeySelective(UserPermision record);

    int updateByPrimaryKey(UserPermision record);
}