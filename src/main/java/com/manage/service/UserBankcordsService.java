package com.manage.service;

import com.manage.entity.UserBankcords;

public interface UserBankcordsService {
    int deleteByPrimaryKey(Long cardid);

    int insert(UserBankcords record);

    int insertSelective(UserBankcords record);

    UserBankcords selectByPrimaryKey(Long cardid);

    int updateByPrimaryKeySelective(UserBankcords record);

    int updateByPrimaryKey(UserBankcords record);
}
