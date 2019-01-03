package com.manage.service.impl;

import com.manage.common.commonUtil.DateUtil;
import com.manage.dao.UserLevelLogMapper;
import com.manage.entity.UserLevelLog;
import com.manage.service.UserLevellLogService;
import com.manage.vo.UserLevelLogView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserLevellLogServiceImpl implements UserLevellLogService {
    @Autowired
    private UserLevelLogMapper mapper;
    @Override
    public int deleteByPrimaryKey(Long logid) {
        return mapper.deleteByPrimaryKey(logid);
    }

    @Override
    public int insert(UserLevelLog record) {
        return mapper.insert(record);
    }

    @Override
    public int insertSelective(UserLevelLog record) {
        return mapper.insertSelective(record);
    }

    @Override
    public UserLevelLog selectByPrimaryKey(Long logid) {
        return mapper.selectByPrimaryKey(logid);
    }

    @Override
    public UserLevelLog selectUserLevelLog(UserLevelLog log) {
        return mapper.selectUserLevelLog(log);
    }

    @Override
    public int updateByPrimaryKeySelective(UserLevelLog record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserLevelLog record) {
        return mapper.updateByPrimaryKey(record);
    }

    @Override
    public List<UserLevelLogView> selectUserLevelInfo(UserLevelLog request) {
        List<UserLevelLogView> list = mapper.selectUserLevelInfo(request);
        // 计算已领用天数
        Date beginDate = null;
        for (int j = 0; j < list.size(); j++) {
            UserLevelLogView v = list.get(j);
            Date endDate = v.getTime();
            if (j==0){
                beginDate = endDate;
                continue;}
            else if(j<list.size() -1){
                list.get(j-1).setActDaynum(-DateUtil.differentDays(beginDate,endDate));
            }else {
                list.get(j-1).setActDaynum(-DateUtil.differentDays(beginDate,endDate));
                list.get(j).setActDaynum(-DateUtil.differentDays(endDate,new Date()));
            }
        }
        return list;
    }
}
