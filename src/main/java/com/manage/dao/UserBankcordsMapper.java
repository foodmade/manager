package com.manage.dao;

import com.manage.entity.UserBankcords;

public interface UserBankcordsMapper {
    int deleteByPrimaryKey(Long cardid);

    int insert(UserBankcords record);

    int insertSelective(UserBankcords record);

    UserBankcords selectByPrimaryKey(Long cardid);

    int updateByPrimaryKeySelective(UserBankcords record);

    int updateByPrimaryKey(UserBankcords record);
}