package com.manage.service.impl;

import com.manage.common.enums.DroaState;
import com.manage.common.enums.ExceptionEnum;
import com.manage.dao.DroaMapper;
import com.manage.entity.Droa;
import com.manage.entity.Page;
import com.manage.service.DroaService;
import com.manage.vo.DroaApply;
import com.manage.vo.DroaScope;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DroaServiceImpl implements DroaService {

    @Autowired
    private DroaMapper droaMapper;

    @Override
    public List qryDroaList(DroaScope scope) {
        return droaMapper.qryDroaList(scope);
    }
    @Override
    public Page qryDroaListCount(DroaScope scope) {
        Page page = scope.getPage();
        page.setRows(droaMapper.qryDroaListCount(scope));
        return page;
    }

    @Override
    public void doDroa(DroaApply droa) {
        if(droa.getUserid()==null || droa.getDroaamount()==null) {
            throw new RuntimeException(ExceptionEnum.PARAMEMPTYPEROR.getMessage());
        }
        droa.setApplytime(new Date());
        droa.setState(DroaState.APPLY.getCode());
        droaMapper.insert(droa);
    }

    @Override
    public void handleDroa(Droa droa) {
        if(droa==null || droa.getDroaid()==null || droa.getExeuserid()==null){
            throw new RuntimeException(ExceptionEnum.PARAMEMPTYPEROR.getMessage());
        }
        droa.setState(DroaState.EXE.getCode());
        droa.setExetime(new Date());
        droaMapper.updateByPrimaryKeySelective(droa);
    }

    @Override
    public Double qryToPayAmount(DroaScope scope) {
        return droaMapper.qryToPayAmount(scope)==null?0.00:droaMapper.qryToPayAmount(scope);
    }
}
