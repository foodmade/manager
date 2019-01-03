package com.manage.controller;

import com.alibaba.fastjson.JSON;
import com.manage.annotation.BaseChek;
import com.manage.common.commonUtil.CommonUtil;
import com.manage.common.commonUtil.DateUtil;
import com.manage.common.enums.DroaState;
import com.manage.common.enums.UserLevelEnum;
import com.manage.common.model.BaseResult;
import com.manage.entity.*;
import com.manage.service.*;
import com.manage.vo.DroaScope;
import com.manage.vo.UserScope;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author chenlu
 * @Description 查询统一前端处理
 * @since 2018-7-22
 */

@Controller
public class QryDataController {

    @Autowired
    private UserAmountDetailService amountDetailService;
    @Autowired
    private UserLevellLogService levellLogService;
    @Autowired
    private CommonUtil commonUtilService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserBalanceService balanceService;
    @Autowired
    private DroaService droaService;
    @Autowired
    private UserLevelService levelService;

    /**
     * @Description 查询工资明细记录
     * @author chenlu
     * @since 2018-7-22
     */
    @RequestMapping(value = "/getUserAmountDetail.do", method = RequestMethod.POST)
    @BaseChek(needCheckToken = false, beanClazz = UserAmountDetail.class)
    @ResponseBody
    public BaseResult qryUserAmountDetail(@RequestAttribute UserAmountDetail request) {
        List<UserAmountDetail> list = amountDetailService.selectDetail(request);
        Map map = amountDetailService.selectDetailSum(request);
        Map re = new HashMap();
        re.put("data" ,list);
        re.put("sum" ,map);
        return new BaseResult(re);
    }

    /**
     * @Description 查询余额信息
     * @author chenlu
     * @since 2018-7-22
     */
    @RequestMapping(value = "/getUserAmount.do", method = RequestMethod.POST)
    @BaseChek(needCheckToken = false, beanClazz = Users.class)
    @ResponseBody
    public BaseResult qryUserAmount(@RequestAttribute Users request) {
        UserBalance balance = new UserBalance();
        balance.setUserid(request.getUserid());
        // 余额信息
        balance = balanceService.selectByPrimaryKey(balance);
        // 提现申请
        DroaScope scope = new DroaScope();
        scope.setState(DroaState.APPLY.getCode());
        scope.setUserid(request.getUserid());
        // 提现待处理金额
        Double toPayAmount = droaService.qryToPayAmount(scope);
        Map rel = new HashMap();
        if(balance==null){
            balance=new UserBalance();
        }
        rel.put("balance", balance.getBalance());
        rel.put("toPayAmount", toPayAmount);
        return new BaseResult(rel);
    }

    /**
     * @Description 查询用户级别
     * @author chenlu
     * @since 2018-7-22
     */
    @RequestMapping(value = "/getUserLevel.do", method = RequestMethod.POST)
    @BaseChek(needCheckToken = false, beanClazz = UserLevelLog.class)
    @ResponseBody
    public BaseResult qryUserLevle(@RequestAttribute UserLevelLog request) {
        BaseResult result = new BaseResult(levellLogService.selectUserLevelInfo(request));
        return result;
    }

    /**
     * @Description 通过用户查询该用户的菜单列表
     * @author chenlu
     * @since 2018-7-29
     */
    @RequestMapping(value = "/getMenuTreeByUser.do", method = RequestMethod.POST)
    @BaseChek(needCheckToken = false, beanClazz = Users.class)
    @ResponseBody
    public BaseResult getMenuTreeByUser(@RequestAttribute Users request) {
        List<MenuTree> menuTreeByUser = commonUtilService.getMenuTreeByUser(request.getUserid());
        List result = new ArrayList();
        for (int i = 0; i < menuTreeByUser.size(); i++) {
            MenuTree menuTree = menuTreeByUser.get(i);
            if (menuTree.getPid() == null) {
                for (int j = 0; j < menuTreeByUser.size(); j++) {
                    if (menuTree.getMenuid().equals(menuTreeByUser.get(j).getPid())) {
                        menuTree.getChildren().add(menuTreeByUser.get(j));
                    }
                }
                result.add(menuTree);
            }
        }

        return new BaseResult(result);
    }
    /**
     * @Description 获取所有等级
     * 数据：日期：2018-7统计的数据为：2018-6-25 <= date < 2018-7-25的数据
     * date="2018-07"
     * @author chenlu
     * @since 2018-7-29
     */
    @RequestMapping(value = "/getAllLel.do", method = RequestMethod.POST)
    @BaseChek(needCheckToken = false,needCheckParameter = false)
    @ResponseBody
    public BaseResult getAllLel() {
        return  new BaseResult(levelService.selectAll());
    }
    /**
     * @Description 收支报表
     * 数据：日期：2018-7统计的数据为：2018-6-25 <= date < 2018-7-25的数据
     * date="2018-07"
     * @author chenlu
     * @since 2018-7-29
     */
    @RequestMapping(value = "/getStatement.do", method = RequestMethod.POST)
    @BaseChek(needCheckToken = false, beanClazz = UserScope.class)
    @ResponseBody
    public BaseResult getStatement(@RequestAttribute UserScope request) {
        List data = new ArrayList<>();
        // 组装查询参数：如果日期为空，那么查询最早开通会员的时间到当前时间的期间。如果不为空，那么查询指定月份的数据
        String date = request.getDate();
        if (StringUtils.isEmpty(date)) {
            // 不传日期，默认查询当年数据
            List<String> list = new ArrayList<>();
            try {
                String year = new SimpleDateFormat("yyyy").format(new Date());
                list = DateUtil.getMonthBetween(year+"-01-01", year+"-12-31");
            } catch (Exception e) {
                e.printStackTrace();
            }
            for(int i=0;i<list.size();i++){
                data.add(balanceService.getDateForMonth(list.get(i)));
            }
        } else {
            data.add(balanceService.getDateForMonth(date));
        }
        return new BaseResult(data);
    }
    /**
     * @Description 收支报表--支出明细
     * 数据：日期：2018-7统计的数据为：2018-6-25 <= date < 2018-7-25的数据
     * date="2018-07"
     * @author chenlu
     * @since 2018-7-29
     */
    @RequestMapping(value = "/getStatementDetail.do", method = RequestMethod.POST)
    @BaseChek(needCheckToken = false, beanClazz = UserScope.class)
    @ResponseBody
    public BaseResult getStatementDetail(@RequestAttribute UserScope request) {
        return new BaseResult(balanceService.getDateForMonthDetail(request.getDate()));
    }

}
