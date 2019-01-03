package com.manage.service;

import com.manage.entity.UserLevelLog;
import com.manage.vo.UserLevelLogView;

import java.util.List;

public interface UserLevellLogService {
    int deleteByPrimaryKey(Long logid);

    int insert(UserLevelLog record);

    int insertSelective(UserLevelLog record);

    UserLevelLog selectByPrimaryKey(Long logid);

    UserLevelLog selectUserLevelLog(UserLevelLog log);

    int updateByPrimaryKeySelective(UserLevelLog record);

    int updateByPrimaryKey(UserLevelLog record);

    List<UserLevelLogView> selectUserLevelInfo(UserLevelLog request);
}
