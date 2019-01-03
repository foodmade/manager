package com.manage.job;

import com.alibaba.fastjson.JSON;
import com.manage.common.Const;
import com.manage.common.commonUtil.DateUtil;
import com.manage.common.commonUtil.DateUtils;
import com.manage.common.enums.UserLevelEnum;
import com.manage.dao.UserLevelLogMapper;
import com.manage.dao.UsersMapper;
import com.manage.entity.UserLevel;
import com.manage.entity.UserLevelLog;
import com.manage.entity.Users;
import com.manage.service.impl.UserServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class UpdateUserLevelJob {
    private static Logger logger = Logger.getLogger(UpdateUserLevelJob.class);

    /**
     * 昨日用户最新登记记录表
     */
    private static HashMap<String,ConcurrentHashMap<Long,Integer>> userLevelTable =
                                                new HashMap<>();

    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private AutoAccountingJob autoAccountingJob;
    @Autowired
    private UserLevelLogMapper userLevelLogMapper;


    /**
     * 更新所有用户级别
     */
    @Scheduled(cron = "0 30 0 * * ? ")
    public void updateUserLevel(){
        //清空内存表
        userLevelTable.clear();
        //获取所有用户
        List<Users> allUsers = usersMapper.selectAllUser();
        String scanDateNode = DateUtils.getBeforeDays(new Date(),1);
        if(allUsers==null || allUsers.size()==0){
            logger.warn("【当日扫描时间节点：{"+scanDateNode+"}的总用户数为0 退出执行】");
            return;
        }
        logger.info("【开始扫描时间节点：{"+ scanDateNode +"} 用户总数：{"+allUsers.size()+"}】");
        //获取所有树节点
        ConcurrentLinkedQueue<Long> userIdQueue = parserAllUserId(allUsers);
        //内存建立基础表
/*        HashMap<Long,Users> indexMap = userService.parserAllSingleUserInfo(allUsers);*/
        //解析子主节点信息
        HashMap<Long, Set<Long>> baseUserMap = userService.parserNodeUserMap(allUsers);
        //每个节点所有下线列表
        ConcurrentHashMap<Long,HashMap<Integer,Set<Long>>> outputMap = new ConcurrentHashMap<>();
        Long start = System.currentTimeMillis();
        //循环所有树节点 开始遍历内存表 获取每个树节点的所有子节点
        for (Long userId:userIdQueue){
            outputMap.put(userId,userService.traverseTreeByAllNodeId(baseUserMap,userId));
        }
        //解析每个区域的人数
        HashMap<Long,HashMap<Integer,Integer>> areaCntMap = parserAreaUserCnt(outputMap);
        logger.info("解析过后的结果:"+JSON.toJSONString(areaCntMap));
        Long endTime = System.currentTimeMillis();
        logger.info("【树节点计算完毕 耗时:{"+ (endTime - start) +"}】");
        logger.debug("【计算结果集：】"+ JSON.toJSONString(areaCntMap));
        //更新用户信息
        memoryUpdateData(allUsers,areaCntMap);
//        updateUserInfoTXCommit(allUsers);
        logger.info("【更新数据完成 耗时:{"+ (System.currentTimeMillis()-endTime) +"}】");
        autoAccountingJob.execute();
    }

    private HashMap<Long, HashMap<Integer, Integer>> parserAreaUserCnt(ConcurrentHashMap<Long,HashMap<Integer,Set<Long>>> outputMap) {

        HashMap<Long,HashMap<Integer,Integer>> result = new HashMap<>();

        HashMap<Integer,Set<Long>> childMap;
        HashMap<Integer,Integer> userAreaCntMap;
        for (Long userId:outputMap.keySet()){
            childMap = outputMap.get(userId);
            for (Integer area:childMap.keySet()){
                userAreaCntMap = result.get(userId);
                if(userAreaCntMap == null){
                    userAreaCntMap = new HashMap<>();
                }
                userAreaCntMap.put(area,childMap.get(area).size());
                result.put(userId,userAreaCntMap);
            }
        }
        return result;
    }

    public void updateUserInfoTXCommit(List<Users> allUsers) {
        for (Users users:allUsers){
            usersMapper.updateByPrimaryKey(users);
        }
    }

    /**
     * 内存中更新用户级别
     */
    private void memoryUpdateData(List<Users> allUsers,HashMap<Long,HashMap<Integer,Integer>> areaCntMap){
        HashMap<Long,Users> newLevelMap = new HashMap<>();
        Long userId;
        HashMap<Integer,Integer> userAreaCnt;
        Integer oneAreaCnt=0,twoAreaCnt=0;
        Long newLevelId;
        //内存中更新用户信息
        for (Users users:allUsers){
            userId = users.getUserid();
            if(userId == null){
                continue;
            }
            userAreaCnt = areaCntMap.get(userId);
            if(userAreaCnt == null){
                userAreaCnt = new HashMap<>();
            }
            //判断各个区域人数是否满足条件
            oneAreaCnt = userAreaCnt.get(1) == null ? 0: userAreaCnt.get(1);
            twoAreaCnt = userAreaCnt.get(2) == null ? 0: userAreaCnt.get(2);

            Long oneArealevel = parserAreaIsMeet(oneAreaCnt);
            Long twoAreaLevel = parserAreaIsMeet(twoAreaCnt);
            newLevelId = comparisonInt(oneArealevel,twoAreaLevel);

            if(!newLevelId.equals(users.getLevelid())){
                newLevelMap.put(users.getUserid(),users);
            }
            //设置新的等级
            users.setLevelid(newLevelId);
        }
        saveLevelChange(newLevelMap);
    }

    private void saveLevelChange(HashMap<Long, Users> newLevelMap) {

        if(newLevelMap.isEmpty()){
            return;
        }
        List<UserLevelLog> insertList = new ArrayList<>();
        logger.info("【存在等级调整记录 开始插入等级调整日志表】");
        UserLevelLog userLevelLog;
        Users users;
        for (Long levelId:newLevelMap.keySet()){
            userLevelLog = new UserLevelLog();
            users = newLevelMap.get(levelId);
            userLevelLog.setUserid(users.getUserid());
            userLevelLog.setTime(new Date());
            userLevelLog.setLevelid(users.getLevelid());
            userLevelLog.setReason(Const.LEVELCHANGEDESC);
            insertList.add(userLevelLog);
            usersMapper.updateByPrimaryKey(users);
        }
        userLevelLogMapper.insertBatch(insertList);
    }

    private Long parserAreaIsMeet(Integer oneAreaCnt) {

        if(AutoAccountingJob.getGlobalLevelMap().isEmpty()){
            autoAccountingJob.initLevelInfo();
        }
        //获取等级基本信息
        HashMap<Long,UserLevel> globalLevelMap = AutoAccountingJob.getGlobalLevelMap();
        //解析当前人数属于哪个区域
        for (UserLevel userLevel:globalLevelMap.values()){
            //区间头
            Integer head = userLevel.getUnums();
            Integer tail = userLevel.getUnume();
            if(tail == -1 && oneAreaCnt >= head){
                return userLevel.getLevelid();
            }
            if(oneAreaCnt>=head && oneAreaCnt<=tail){
                return userLevel.getLevelid();
            }
        }
        return Long.parseLong(UserLevelEnum.LEVLE1.getLevelCode()+"");
    }

    private Long comparisonInt(Long i1,Long i2){
        if(i1 < i2){
            return i1;
        }
        if(i2 < i1){
            return i2;
        }
        return i1;
    }

    private ConcurrentLinkedQueue<Long> parserAllUserId(List<Users> allUsers) {

        ConcurrentLinkedQueue<Long> result = new ConcurrentLinkedQueue<>();
        for (Users users:allUsers){
            result.add(users.getUserid());
        }
        return result;
    }

    public static HashMap<String, ConcurrentHashMap<Long, Integer>> getUserLevelTable() {
        return userLevelTable;
    }
}
