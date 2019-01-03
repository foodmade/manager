package com.manage.dao;

import com.manage.entity.Droa;
import com.manage.vo.DroaScope;

import java.util.List;

public interface DroaMapper {
    int deleteByPrimaryKey(Long droaid);

    int insert(Droa record);

    int insertSelective(Droa record);

    Droa selectByPrimaryKey(Long droaid);

    int updateByPrimaryKeySelective(Droa record);

    int updateByPrimaryKey(Droa record);

    List qryDroaList(DroaScope scope);

    Double selectByUserIdAmount(Long userid);

    Integer qryDroaListCount(DroaScope scope);

    Double qryToPayAmount(DroaScope scope);
}