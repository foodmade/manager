package com.manage.job;

import com.manage.common.commonUtil.DateUtil;
import com.manage.common.enums.UserLevelEnum;
import com.manage.dao.UserLevelMapper;
import com.manage.entity.*;
import com.manage.service.*;
import com.manage.vo.UserScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 * @Description 自动账务处理
 *
 * @author chenlu
 *
 * @since 2018-7-22
 */

@Component
public class AutoAccountingJob {
    private static Logger logger = Logger.getLogger(AutoAccountingJob.class.toString());

    @Autowired
    private UserService userService;
    @Autowired
    private UserAmountDetailService detailService;
    @Autowired
    private UserBalanceService balanceService;
    @Autowired
    private UserBalanceLogService logService;
    @Autowired
    private UserLevellLogService levellLogService;
    @Autowired
    private UserLevelMapper userLevelMapper;
    /**
     * @Description 做帐逻辑：
     *          1、会员级别会影响会员日结工资
     *              等级列表
     *                  等级  金额  天数
     *                  普通会员 0 0
     *                  员工骨干 190 100
     *                  组长 390 120
     *                  班长 690 140
     *                  主管 1090 160
     *                  主任 1590 180
     *                  经理 2190 200
     *          2、用户级别有领用时间限制，超过时间后，金额为0
     *          3、用户等级提升时点在新增用户时，不在此处
     *
     * @author chenlu
     *
     * @since 2018-7-22
     */
    public void execute(){
        // 查询需要做账的会员列表和级别。
        UserScope scope = new UserScope();
        Integer[] levls = new Integer[]{
                UserLevelEnum.LEVLE2.getLevelCode(),
                UserLevelEnum.LEVLE3.getLevelCode(),
                UserLevelEnum.LEVLE4.getLevelCode(),
                UserLevelEnum.LEVLE5.getLevelCode(),
                UserLevelEnum.LEVLE6.getLevelCode(),
                UserLevelEnum.LEVLE7.getLevelCode()
        };
        scope.setUserLevls(levls);
        // 正常状态的用户
        scope.setState(0);
        List<Users> list = userService.qryUserList(scope);
        // 根据当前用户级别生成日均工资明细，分享金额为1~2的随机金额
        if(list==null || list.size() == 0){
            return;
        }
        logger.info("本日发工资用户数量为："+list.size());
        Long startTime = System.currentTimeMillis();
        for (Users user:list){
            // 获取当前用户等级
            Long levlId = user.getLevelid();
            // 获取该等级的日工资
            UserLevelEnum  lel = UserLevelEnum.getAmountBylevelCode(levlId);
            if(lel == null){
                continue;
            }
            // 用户级别有领用时间限制，超过时间后，金额为0
            Integer daynum = lel.getDaynum();
            // 查询用户这个级别的调整时间，计算出到当前日期的天数。
            UserLevelLog lelLog = new UserLevelLog();
            lelLog.setLevelid(user.getLevelid());
            lelLog.setUserid(user.getUserid());
            lelLog =  levellLogService.selectUserLevelLog(lelLog);
            if(lelLog==null){
                continue;
            }else {
                int day = DateUtil.differentDays(lelLog.getTime(),new Date());
                if(day>daynum){
                    continue;
                }
            }
            UserAmountDetail wages = new UserAmountDetail();

            Random rd = new Random();
            Float bonusAmount = new BigDecimal(rd.nextFloat()+1).floatValue();

            wages.setAmount(lel.getAmount().floatValue());
            wages.setComamount(bonusAmount);
            wages.setTime(new Date());
            wages.setUserid(user.getUserid());
            //wages.setTax();
            // 判断是否已有数据，如果无，则插入
            UserAmountDetail detailScope =  new UserAmountDetail();
            detailScope.setUserid(user.getUserid());
            detailScope.setTime(new Date());
            List<UserAmountDetail> result = detailService.selectDetail(detailScope);

            if(result==null || result.size()<1){
                // 插入明细
                detailService.insert(wages);
                // 更新余额
                UserBalance s = new UserBalance();
                s.setUserid(user.getUserid());
                UserBalance balance = balanceService.selectByPrimaryKey(s);

                UserBalanceLog log = new UserBalanceLog();
                if (balance==null){
                    // 新增
                    balance = new UserBalance();
                    log.setOldbalance(0.00F);
                    balance.setUserid(user.getUserid());
                    balance.setBalance(lel.getAmount().floatValue()+bonusAmount);
                    balanceService.insert(balance);
                }else {
                    // 更新
                    log.setOldbalance(balance.getBalance());
                    balance.setBalance(balance.getBalance()+lel.getAmount().floatValue()+bonusAmount);
                    balanceService.updateByPrimaryKey(balance);
                }
                // 记录日志
                log.setBalid(balance.getBalid());
                log.setAmount(lel.getAmount().floatValue()+bonusAmount);
                log.setType(1);
                log.setOptiontype(1);
                logService.insert(log);
            }
        }
        System.out.println("耗时:"+(System.currentTimeMillis()-startTime));
    }

    private static final HashMap<Long,UserLevel> globalLevelMap = new HashMap<>();

    public static HashMap<Long, UserLevel> getGlobalLevelMap() {
        return globalLevelMap;
    }

    /**
     * 初始化级别信息 (级别Id--级别描述)
     */
    @Scheduled(fixedRate = 1000*60*10)
    public void initLevelInfo(){
        List<UserLevel> userLevels = userLevelMapper.selectAll();
        if(userLevels == null || userLevels.isEmpty()){
            return ;
        }
        for (UserLevel userLevel:userLevels){
            globalLevelMap.put(userLevel.getLevelid(),userLevel);
        }
    }
}
