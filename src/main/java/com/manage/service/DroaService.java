package com.manage.service;

import com.manage.entity.Droa;
import com.manage.entity.Page;
import com.manage.vo.DroaApply;
import com.manage.vo.DroaScope;

import java.util.List;

/**
 * @Description 提现处理服务
 *
 * @author chenlu
 *
 * @since 2018-7-22
 */
public interface DroaService {

    /**
     * @Description 查询提现记录
     *
     * @author chenlu
     *
     * @since 2018-7-22
     */
    public List qryDroaList(DroaScope scope);
    /**
     * @Description 查询提现记录
     *
     * @author chenlu
     *
     * @since 2018-7-22
     */
    public Page qryDroaListCount(DroaScope scope);
    /**
     * @Description 提现处理服务
     *
     * @author chenlu
     *
     * @since 2018-7-22
     */
    public void doDroa(DroaApply droa);
    /**
     * @Description 提现处理服务
     *
     * @author chenlu
     *
     * @since 2018-7-22
     */
    public void handleDroa(Droa droa);

    /**
     * 查询待处理提现金额纸盒
     * @param scope
     * @return
     */
    Double qryToPayAmount(DroaScope scope);
}
