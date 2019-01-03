package com.manage.service.impl;

import com.manage.common.Const;
import com.manage.common.commonUtil.*;
import com.manage.common.enums.ExceptionEnum;
import com.manage.common.enums.PermissionEnum;
import com.manage.common.enums.UserLevelEnum;
import com.manage.common.model.BaseResult;
import com.manage.common.redisUtil.RedisClient;
import com.manage.dao.*;
import com.manage.entity.*;
import com.manage.job.AutoAccountingJob;
import com.manage.job.UpdateUserLevelJob;
import com.manage.service.UserService;
import com.manage.vo.*;
import com.manage.vo.in.UserInRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

@Service
public class UserServiceImpl implements UserService {

    private static Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private UserBalanceMapper userBalanceMapper;
    @Autowired
    private DroaMapper droaMapper;
    @Autowired
    private UserAmountDetailMapper userAmountDetailMapper;
    @Autowired
    private UserLevelMapper userLevelMapper;
    @Autowired
    private UserLevelLogMapper userLevelLogMapper;
    @Autowired
    private RedisClient redisClient;
    @Autowired
    private UserBankcordsMapper userBankcordsMapper;
    @Autowired
    private Properties commonProperties;
    @Autowired
    private UpdateUserLevelJob updateUserLevelJob;

    @Override
    public BaseResult createUser(Users users, UserResponse response) {

        //参数检查
        Boolean validStatus = checkParams(users);

        if (!validStatus) {
            return new BaseResult(ExceptionEnum.PARAMEMPTYPEROR);
        }

        InvalidMaxCnt invalidMaxCnt = new InvalidMaxCnt(usersMapper);
        invalidMaxCnt.builder(users);
        invalidMaxCnt = invalidMaxCnt.checkUserName()
                .checkPidIsExist();

        if (!invalidMaxCnt.getFlag()) {
            logger.warn("【注册前置检查操作失败 message:" + invalidMaxCnt.getExceptionEnum().getMessage() + "】");
            return new BaseResult(invalidMaxCnt.getExceptionEnum());
        }
        //开始执行写入操作
        return new BaseResult(doExecuteInsert(users,response));
    }

    @Override
    public BaseResult initSuperAdminUser(String initPwd) {
        if(!commonProperties.getProperty("initAdminPwd").equals(initPwd)){
            return new BaseResult(ExceptionEnum.PERMISSIONERR);
        }
        Users users = new Users();
        users.setAddress("default address,please update address");
        users.setUsername("超级管理员");
        users.setUserno("admin");
        users.setPid(null);
        users.setRegisttime(new Date());
        users.setState(Const.STATIS_ZERO);
        users.setLevelid(Long.parseLong(UserLevelEnum.LEVLE7.getLevelCode()+""));
        users.setPerid(Long.parseLong(PermissionEnum.SUPER_ADMIN.getPre_level()+""));
        users.setTleno("18224449005");
        users.setIdcord("123456780");
        users.setArea(null);
        users.setToppid(fetchAdminParentNodeId());
        users.setHeadiconurl("");
        insertUser(users);
        return new BaseResult(ExceptionEnum.SUCCESS);
    }

    @Override
    public BaseResult updateUserLevel(String pwd) {
        if(!commonProperties.getProperty("initAdminPwd").equals(pwd)){
            return new BaseResult(ExceptionEnum.PERMISSIONERR);
        }
        updateUserLevelJob.updateUserLevel();
        return new BaseResult(ExceptionEnum.SUCCESS);
    }

    @Override
    public BaseResult addAdminUser(Users users,UserResponse userResponse) {

        InvalidMaxCnt invalidMaxCnt = new InvalidMaxCnt(usersMapper);
        invalidMaxCnt.builder(users);
        invalidMaxCnt = invalidMaxCnt.checkUserName();
        if(!invalidMaxCnt.getFlag()){
            return new BaseResult(invalidMaxCnt.getExceptionEnum());
        }
        //随机获取管理员唯一标识 用于树节点
        String nodeId = fetchAdminParentNodeId();
        //直接和超级管理员绑定
        nodeId = buildNodeId(userResponse.getToppid(),nodeId);
        //设置基点
        users.setToppid(nodeId);
        //管理员不属于任何区域
        users.setArea(-1);
        users.setPid(userResponse.getUserId());
        users.setPerid(Long.parseLong(PermissionEnum.find(Const.ADMIN,PermissionEnum.EMPLOYEE).getPre_level()+""));
        users.setRegisttime(DateUtils.getAfterDayTime(new Date(),0));
        users.setState(Const.STATIS_ZERO);
        users.setLevelid(Long.parseLong(UserLevelEnum.LEVLE1.getLevelCode()+""));
        return new BaseResult(insertUser(users));
    }

    @Override
    public BaseResult updateChildUserInfo(Users request, UserResponse userResponse) {
        if(StringUtils.isEmpty(request.getUserid()+"")){
            return new BaseResult(ExceptionEnum.PARAMEMPTYPEROR);
        }
        //判断被更改人是否属于登陆用户的下线 不是则为异常操作
        Boolean requestInvaild = invalidChildNode(request.getUserid(),userResponse.getToppid(),userResponse.getUserId());
        if(!requestInvaild){
            return new BaseResult(ExceptionEnum.INVALIDREQUEST);
        }
        int cnt = usersMapper.updateByPrimaryKeySelective(request);
        if(cnt==0){
            return new BaseResult(ExceptionEnum.EXECUTEFAIL);
        }
        return new BaseResult(ExceptionEnum.SUCCESS);
    }

    @Override
    public BaseResult checkMeetTreeArea(Users request, UserResponse userResponse) {
        if(StringUtils.isEmpty(request.getUserno())){
            return new BaseResult(ExceptionEnum.PARAMEMPTYPEROR);
        }
        //获取节点id
        Users users = usersMapper.selectByUserNo(request.getUserno());
        if(users == null){
            return new BaseResult(ExceptionEnum.REFERRERNOTFOUND);
        }
        UserInRequest userInRequest = new UserInRequest();
        userInRequest.setToppid(users.getToppid());
        userInRequest.setUserid(users.getUserid());
        List<Users> allChildList = usersMapper.selectAllChildNodeUser(userInRequest);
        if(allChildList == null){
            allChildList = new ArrayList<>();
        }
        Integer meetCnt = Integer.parseInt(commonProperties.getProperty("threeOnlineMeet")+"");
        return new BaseResult(allChildList.size()>=meetCnt);
    }

    /**
     * 判断会员从属关系
     * @param childId  下线账户id
     * @param parentToppid  上线节点id
     * @return
     */
    private Boolean invalidChildNode(Long childId, String parentToppid,Long parentId) {
        List<Users> childList = getChildList(parentToppid,parentId);
        for (Users users:childList){
            if(childId.equals(users.getUserid())){
                return true;
            }
        }
        return false;
    }

    private ExceptionEnum insertUser(Users users){
        try {
            usersMapper.insert(users);
            //初始化账户级别信息
            editorUserLevelInfo(users,UserLevelEnum.LEVLE1);
            //初始化余额信息
            editorUserBalanceInfo(users,Const.STATIS_ZERO);
        } catch (Exception e) {
            logger.error("写入用户信息时发生异常 e:" + e.getMessage());
            return ExceptionEnum.DATABASEERROR;
        }
        return ExceptionEnum.SUCCESS;
    }

    /**
     * 组装数据结构
     *          A ：设计2个标识符 代表层级和区域  (第几层和第几区)
     *          B ：查询时  只需要模糊查询即可 查询子节点使用'aab%'
     */
    private ExceptionEnum doExecuteInsert(Users users, UserResponse response) {
        //获取推荐人信息
        Users parentUser = usersMapper.selectByUserNo(users.getPerUserNo());
        if(parentUser == null){
            return ExceptionEnum.REFERRERNOTFOUND;
        }
        if(users.getArea() == null){
            return ExceptionEnum.PARAMEMPTYPEROR;
        }
        //获取推荐人节点id
        String nodeId = parentUser.getToppid();
        //生成新的树节点Id
        String treeNodeId = nodeId+users.getArea()+"|";
        //检查节点是否已经被占用
        Users nodeUser = usersMapper.selectByTopPid(treeNodeId);
        if(nodeUser != null){
            return ExceptionEnum.AREAOCCUPY;
        }
        users.setToppid(treeNodeId);
        users.setPid(parentUser.getUserid());
        users.setState(Const.STATIS_TWO);
        users.setPerid(Long.parseLong(PermissionEnum.EMPLOYEE.getPre_level()+""));
        users.setRegisttime(DateUtils.getAfterDayTime(new Date(),0));
        users.setLevelid(Long.parseLong(UserLevelEnum.LEVLE1.getLevelCode()+""));
        repairParams(users, response);
        return insertUser(users);
    }

    /**
     * 更新账户信息  账户余额表
     */
    private void editorUserLevelInfo(Users users,UserLevelEnum userLevelEnum){
        UserLevelLog record = getLevelLogModel(users.getUserid(),Long.parseLong(userLevelEnum.getLevelCode()+""));
        //查看是否存在
        UserLevelLog userLevelLog = userLevelLogMapper.selectUserLevelByUserId(users.getUserid());
        if(userLevelLog == null){
            userLevelLogMapper.insert(record);
        }else{
            userLevelLogMapper.updateByPrimaryKeySelective(record);
        }
    }

    private void editorUserBalanceInfo(Users users, Integer balance) {
        UserBalance userBalance = getBalanceModel(users.getUserid(),balance);
        if(userBalanceMapper.selectByUserId(userBalance.getUserid())==null){
            userBalanceMapper.insert(userBalance);
        }else{
            userBalanceMapper.updateByPrimaryKeySelective(userBalance);
        }
    }

    private UserBalance getBalanceModel(Long userid, Integer balance) {
        UserBalance result = new UserBalance();
        result.setUserid(userid);
        result.setBalance(Float.parseFloat(balance+""));
        return result;
    }


    private UserLevelLog getLevelLogModel(Long userId,Long levelId){
        UserLevelLog result = new UserLevelLog();
        result.setUserid(userId);
        result.setLevelid(levelId);
        result.setReason(Const.LEVELDEFAULTDESC);
        result.setTime(DateUtils.getAfterDayTime(new Date(),0));
        return result;
    }

    private void repairParams(Users users, UserResponse response) {
        //设置默认属性为正常状态
        users.setState(Const.STATIS_ZERO);
    }

    @Override
    public Users queryUserByUserName(String userno) {
        return usersMapper.selectByUserNo(userno);
    }

    @Override
    public Users queryUserByPid(Long pid) {
        return usersMapper.selectByPrimaryKey(pid);
    }

    @Override
    public List<Users> qryUserList(UserScope scope) {
        return usersMapper.qryUserList(scope);
    }

    @Override
    public BaseResult deleteUser(Users users) {
        if (users == null || users.getUserid() == null) {
            return new BaseResult(ExceptionEnum.PARAMEMPTYPEROR);
        }
        users.setState(Const.STATIS_TWO);
        int updateCnt = usersMapper.updateByPrimaryKeySelective(users);
        if (updateCnt == 0) {
            logger.warn("更新用户信息失败 method：【deleteUser】");
            return new BaseResult(ExceptionEnum.EXECUTEFAIL);
        }
        return new BaseResult(ExceptionEnum.SUCCESS);
    }

    @Override
    public BaseResult updateUserInfo(Users request) {
        if (request.getUserid() == null) {
            return new BaseResult(ExceptionEnum.PARAMEMPTYPEROR);
        }

        int updateCnt = usersMapper.updateByPrimaryKeySelective(request);
        if (updateCnt == 0) {
            logger.warn("【更新成功的条数为0 user_id:" + request.getUserid() + "】");
            return new BaseResult(ExceptionEnum.EXECUTEFAIL);
        }
        return new BaseResult(ExceptionEnum.SUCCESS);
    }

    @Override
    public BaseResult checkUserNo(Users request) {
        if (request.getUserno() == null) {
            return new BaseResult(ExceptionEnum.PARAMEMPTYPEROR);
        }
        Users users;
        try {
            users = usersMapper.selectByUserNo(request.getUserno());
        } catch (Exception e) {
            logger.error("【根据用户名查询用户详情时发生异常 】e:" + e.getMessage());
            return new BaseResult(ExceptionEnum.DATABASEERROR);
        }
        return new BaseResult(users != null);
    }

    @Override
    public BaseResult invalidPid(Users request) {
        if (request.getPid() == null) {
            return new BaseResult(ExceptionEnum.PARAMEMPTYPEROR);
        }

        return null;
    }

    @Override
    public BaseResult getDetailUserInfo(UserResponse userResponse) {
        if (userResponse == null || userResponse.getUserId() == null) {
            return new BaseResult(ExceptionEnum.TOKENRERROR);
        }

        Users users = getUser(Long.parseLong(userResponse.getUserId() + ""));
        if (users == null) {
            return new BaseResult(ExceptionEnum.ERRUSERID);
        }
        //获取登陆用户下线总人数
        List<Users> childListr = getChildList(userResponse.getToppid(),userResponse.getUserId());
        HashMap<String,Object> result = packageResponseModel(users, userResponse.getRole());
        if(childListr.size() >= 3696){
            result.put("meet",true);
        }
        return new BaseResult(result);
    }

    /**
     * @param userResponse
     * @return
     */

    @Override
    public BaseResult getBalanceInfo(UserResponse userResponse) {
        if(userResponse.getUserId() == null){
            return new BaseResult(ExceptionEnum.TOKENRERROR);
        }
        //获取当前登录用户信息
        Users users = usersMapper.selectByPrimaryKey(userResponse.getUserId());
        if(users == null){
            return new BaseResult(ExceptionEnum.ERRUSERID);
        }
/*        UserInRequest userInRequest = new UserInRequest();
        userInRequest.setToppid(users.getToppid());
        userInRequest.setUserid(users.getUserid());
        //获取用户下所有的下线
        List<Users> allUsers = usersMapper.selectAllChildNodeUser(userInRequest);
        //解析各个区域的人数
        HashMap<String,Object> baseInfo = parserAreaUserInfo(allUsers,new Users());*/


        //获取直系下线的toppid
        HashMap<Integer,Users> childAreaUserInfo = getChildInfo(userResponse.getUserId());
        HashMap<String,Object> baseInfo = parserParentNodeInfo(childAreaUserInfo,users);

        //获取当前用户的余额信息
        UserBalance balanceInfo = fetchUserBalanceInfo(userResponse.getUserId());
        //获取代提现余额
        Double countDroaAmount = fetchUserDroaInfo(userResponse.getUserId());
        //获取日工资累积
        Double countSalary = fetchCountSalary(userResponse.getUserId(), DateUtils.formatDate(new Date(), DateUtils.DATE_FORMAT_YYYY_MM_DD));
        //获取级别工资
        Float daySalary = fetchLevelAmount(userResponse.getLevel());
        baseInfo.put("salarySum", countSalary);
        baseInfo.put("withdraw", countDroaAmount);
        if(balanceInfo == null){
            baseInfo.put("balance", 0);
        }else{
            baseInfo.put("balance", balanceInfo.getBalance());
        }
        baseInfo.put("salary", daySalary);
        return new BaseResult(baseInfo);
    }

    private Float fetchLevelAmount(Long level) {

        UserLevel userLevel = userLevelMapper.selectByPrimaryKey(level);
        if (userLevel == null) {
            return 0.00F;
        }
        return userLevel.getAmount();
    }

    private Double fetchCountSalary(Long userid, String date) {
        Double d;
        try {
            d = userAmountDetailMapper.countSalary(userid, date);
        } catch (Exception e) {
            logger.error("【查询用户工资明细表失败:e】" + e.getMessage());
            return 0.00D;
        }
        return d == null ? 0.00D : d;
    }

    private Double fetchUserDroaInfo(Long userId) {
        Double amount;
        try {
            amount = droaMapper.selectByUserIdAmount(userId);
        } catch (Exception e) {
            logger.error("【查询用户提现表失败 e:】" + e.getMessage());
            return 0.00D;
        }
        return amount == null ? 0.00D : amount;
    }

    private UserBalance fetchUserBalanceInfo(Long userId) {
        UserBalance userBalance;
        try {
            userBalance = userBalanceMapper.selectByUserId(userId);
        } catch (Exception e) {
            logger.error("【查询用户余额表失败 e:】" + e.getMessage());
            return new UserBalance();
        }
        return userBalance;
    }

    private HashMap<String, Object> parserAreaUserInfo(List<Users> allUsers,Users defaultUser) {
        HashMap<String, Object> result = getDefaultAreaMap();
        if(defaultUser == null){
            defaultUser = new Users();
        }
        result.put("userno",defaultUser.getUserno());
        result.put("username", defaultUser.getUsername());
        result.put("registerTime",DateUtils.formatDate(defaultUser.getRegisttime(),"yyyy-MM-dd"));
        Integer oneArea = 0, twoArea = 0, threeArea = 0;
        for (Users users : allUsers) {
            if (users.getArea() == 1) {
                oneArea++;
            } else if (users.getArea() == 2) {
                twoArea++;
            } else if (users.getArea() == 3) {
                threeArea++;
            }
            users.setRegisttime(DateUtils.parseDate(users.getRegisttime().getTime()+"",DateUtils.DATE_FORMAT_YYYY_MM_DD));
            result.put("oneArea",oneArea);
            result.put("twoArea",twoArea);
        }
        if(threeArea>0){
            result.put("threeArea",threeArea);
        }
        return result;
    }

    private HashMap<String, Object> getDefaultAreaMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("oneArea", 0);
        result.put("twoArea", 0);
        result.put("threeArea", 0);
        return result;
    }

    @Override
    public BaseResult getUserInfoById(Users request, UserResponse userResponse) {
        if (request == null || request.getUserid() == null) {
            return new BaseResult(ExceptionEnum.TOKENRERROR);
        }
        Users users = getUser(request.getUserid());
        if (users == null) {
            return new BaseResult(ExceptionEnum.ERRUSERID);
        }
        //获取推荐人信息
        Users parentUser = usersMapper.selectByPrimaryKey(users.getPid());
        users.setPerUserNo(parentUser.getUserno());
        return new BaseResult(packageResponseModel(users, userResponse.getRole()));
    }

    /**
     * 查询条件为空时 默认搜索登录用户本身
     * 1 获取用户下所有的子节点
     */
    @Override
    public BaseResult getUserList(QueryUserListRequest request, UserResponse userResponse){
        HashMap<String, Object> resData = new HashMap<>();
        //前置检查 判断搜索条件是否满足当前登录用户
        Boolean flag = invalidRequest(userResponse,request);
        if(!flag){
            return new BaseResult(resData);
        }
        //适配收支报表逻辑 判断传的时间格式
        compatibility(request);
        request.setPage((request.getPage()-1)*request.getPageSize());
        request.setToppid(userResponse.getToppid());
        if(request.getUserid() == null){
            request.setUserid(userResponse.getUserId());
        }
        //获取当前登陆用户下所有的会员
        List<Users> allUsers = getChildUserData(request,userResponse.getUserNo());
        Integer total = usersMapper.selectSearchQueryCnt(request);
        if(allUsers == null || allUsers.size()==0){
            return new BaseResult(new HashMap<>());
        }
        UserInRequest inRequest;
        List<Users> childList;
        List<HashMap<String,Object>> rows = new ArrayList<>();
        HashMap<String,Object> itemMap;
        //获取每个用户子节点个数
        for (Users users:allUsers){
            inRequest = new UserInRequest();
            inRequest.setToppid(users.getToppid());
            inRequest.setUserid(users.getUserid());
            childList = usersMapper.selectAllChildNodeUser(inRequest);
            if(childList == null){
                childList = new ArrayList<>();
            }
            itemMap = getItemMap(users);
            itemMap.put("recommendCnt",childList.size());
            rows.add(itemMap);
        }
        resData.put("data",rows);
        resData.put("total",total);
        resData.put("page",request.getPage());
        resData.put("pageSize",request.getPageSize());
        return new BaseResult(resData);
    }

    private void compatibility(QueryUserListRequest request) {
        /**
         * 前端是使用收支报表查询的话 只传starTime
         */
        if(request.getStartTime() == null || request.getStartTime().equals("")){
            return;
        }
        if(request.getEndTime()!=null && !request.getEndTime().equals("")){
            return;
        }
        DateFormat formatter = new SimpleDateFormat("yyyy-MM");
        try {
            formatter.parse(request.getStartTime());
            //解析成功说明传的时间格式为YYYY-mm 需要重新计算起始和结束时间
            //解析年 月
            Integer year = Integer.parseInt(request.getStartTime().split("-")[0]);
            Integer month = Integer.parseInt(request.getStartTime().split("-")[1]);
            //获取当月的最后一日日期
            request.setEndTime(DateUtils.getLastDayOfMonth(year,month));
            request.setStartTime(request.getStartTime()+"-01");
        } catch (ParseException e) {
        }

    }

    private Boolean invalidRequest(UserResponse userResponse, QueryUserListRequest request) {
        if(StringUtils.isEmpty(request.getUserno())){
            return true;
        }else{
            //获取搜索用户基本信息
            Users queryUser = usersMapper.selectByUserNo(request.getUserno());
            if(queryUser == null){
                return false;
            }
            return invalidChildNode(queryUser.getUserid(),userResponse.getToppid(),userResponse.getUserId());
        }
    }

    private List<Users> getChildUserData(QueryUserListRequest request, String userNo) {
        List<Users> result = new ArrayList<>();
        Users searchUser;
        String baseUserno;
        if(StringUtils.isEmpty(request.getUserno())){
            baseUserno = userNo;
            searchUser = usersMapper.selectByUserNo(userNo);
        }else{
            baseUserno = request.getUserno();
            searchUser = usersMapper.selectByUserNo(request.getUserno());
        }
        if(searchUser == null){
            return null;
        }
        request.setUserno(null);
        request.setToppid(searchUser.getToppid());
        List<Users> list = usersMapper.selectSearchQuery(request);
        if(list == null || list.size()==0){
            return null;
        }
        //剔除自身
        for (Users users:list){
            if(baseUserno.equals(users.getUserno())){
                continue;
            }
            result.add(users);
        }
        return result;
    }

    class TempChildList{
        private List<Users> oneAreaList = new ArrayList<>();
        private List<Users> twoAreaList = new ArrayList<>();
        private List<Users> adminAreaList = new ArrayList<>();
        private HashMap<Long,List<Users>> adminChildMap = new HashMap<>();
        HashMap<Long,Users> userIndexMap= new HashMap<>();
        private Users oneUser;
        private Users twoUser;
        private List<Users> allUsers = new ArrayList<>();

        public List<Users> getAllUsers() {
            return allUsers;
        }

        public HashMap<Long, List<Users>> getAdminChildMap() {
            return adminChildMap;
        }

        public void setAdminChildMap(HashMap<Long, List<Users>> adminChildMap) {
            this.adminChildMap = adminChildMap;
        }

        public void setAllUsers(List<Users> allUsers) {
            this.allUsers = allUsers;
        }

        public Users getOneUser() {
            return oneUser;
        }

        public void setOneUser(Users oneUser) {
            this.oneUser = oneUser;
        }

        public Users getTwoUser() {
            return twoUser;
        }

        public void setTwoUser(Users twoUser) {
            this.twoUser = twoUser;
        }

        public List<Users> getOneAreaList() {
            return oneAreaList;
        }

        public void setOneAreaList(List<Users> oneAreaList) {
            this.oneAreaList.addAll(oneAreaList);
        }

        public List<Users> getTwoAreaList() {
            return twoAreaList;
        }

        public void setTwoAreaList(List<Users> twoAreaList) {
            this.twoAreaList.addAll(twoAreaList);
        }

        public List<Users> getAdminAreaList() {
            return adminAreaList;
        }

        public void setAdminAreaList(List<Users> adminAreaList) {
            this.adminAreaList.addAll(adminAreaList);
        }

        public HashMap<Long, Users> getUserIndexMap() {
            return userIndexMap;
        }

        public void setUserIndexMap(HashMap<Long, Users> userIndexMap) {
            this.userIndexMap = userIndexMap;
        }
    }

    @Override
    public BaseResult getUserTree(Users request, UserResponse userResponse){

        HashMap<String,Object> result = initReturnDefaultMap();
        Long queryUserId;
        Boolean flag = invalidParams(request,userResponse);
        if(!flag){
            return new BaseResult(new HashMap<>());
        }
        //获取搜索用户
        if(StringUtils.isEmpty(request.getUsername())
                && StringUtils.isEmpty(request.getTleno())
                && StringUtils.isEmpty(request.getUserno())){
            //默认搜索当前登录用户
            queryUserId = userResponse.getUserId();
        }else{
            request.setState(0);
            List<Users> searchList = usersMapper.selectByPrimaryFilter(request);
            if(searchList==null || searchList.size()==0){
                return new BaseResult(new HashMap<>());
            }

//            level1_toppid = searchList.get(0).getToppid();
            queryUserId = searchList.get(0).getUserid();
        }
        //获取父节点信息
        Users parentUser = usersMapper.selectByPrimaryKey(queryUserId);
        //获取直系下线的toppid
        HashMap<Integer,Users> childAreaUserInfo = getChildInfo(queryUserId);
        //获取每个区域的人数情况 根据子节点的toppid进行查询
        /**
         * 1 组装父节点信息
         */
        result.put("level_one",parserParentNodeInfo(childAreaUserInfo,parentUser));
        /**
         * 获取子节点信息
         * 获取子节点信息的子子节点 然后查询下线任务
         */
        result.put("level_two",parserTwoLevelNodeInfo(childAreaUserInfo));
/*        //获取父节点信息
        List<Users> parentList = getChildList(level1_toppid,parentUser.getUserid());*/
       /* //计算每个区的人数
        result.put("level_one",parserAreaUserInfo(parentList,parentUser));*/
/*        List<HashMap<String, Object>> areaTwoList = new ArrayList<>();
        parserTwoChildList(areaTwoList,tempChildList);

        result.put("level_two",areaTwoList);*/
        return new BaseResult(result);
    }

    public HashMap<Integer,Users> getChildInfo(Long userId){
        List<Users> childList = usersMapper.selectByPid(userId);

        if(childList==null || childList.size()==0){
            logger.error("【从节点数量大于2个 异常现象 中断操作】");
            childList = new ArrayList<>();
        }
        //获取直系下线的toppid
        return parserChildAreaToppid(childList);
    }

    private List<HashMap<String,Object>> parserTwoLevelNodeInfo(HashMap<Integer, Users> childAreaUserInfo) {
        if(childAreaUserInfo == null || childAreaUserInfo.size()==0){
            return new ArrayList<>();
        }
        UserInRequest userInRequest;
        List<Users> childList;
        List<HashMap<String,Object>> result = new ArrayList<>();
        HashMap<Integer,Integer> areaCntMap;
        HashMap<String,Object> childMap;
        HashMap<Integer,Users> childAreaUsers;
        for (Integer area:childAreaUserInfo.keySet()){
            Users users = childAreaUserInfo.get(area);
            //获取到子子节点
            childList = usersMapper.selectByPid(users.getUserid());
            //获取直系下线的toppid
            childAreaUsers = parserChildAreaToppid(childList);
            childMap = getDefaultAreaMap();
            childMap.put("userno",users.getUserno());
            childMap.put("username",users.getUsername());

            childMap.put("registerTime",DateUtils.formatDate(users.getRegisttime(),"yyyy-MM-dd"));
            for (Integer childArea:childAreaUsers.keySet()){
                Users childUser= childAreaUsers.get(childArea);
                userInRequest = new UserInRequest();
                userInRequest.setToppid(childUser.getToppid());
//                userInRequest.setUserid(childUser.getUserid());
                childList = usersMapper.selectAllChildNodeUser(userInRequest);
                if(childArea==1 || childArea==-1){
                    childMap.put("oneArea",childList.size());
                }else if(childArea==2){
                    childMap.put("twoArea",childList.size());
                }else if(childArea==3){
                    childMap.put("threeArea",childList.size());
                }
            }

            result.add(childMap);
        }
        return result;
    }

    private HashMap<Integer,Integer> calculateAreaCnt(List<Users> userList,Users baseUser){
        HashMap<Integer,Integer> result = new HashMap<>();
        result.put(1,0);
        result.put(2,0);
        result.put(3,0);
        Integer areaCnt,area;
        for (Users users:userList){
            if(users.getPid().equals(baseUser.getUserid())){
                area = users.getArea() == -1?1:users.getArea();
                areaCnt = result.get(area);
                if(areaCnt == null){
                    areaCnt=0;
                }
                areaCnt++;
                result.put(users.getArea() == -1?1:users.getArea(),areaCnt);
            }else{
                area = baseUser.getArea() == -1?1:baseUser.getArea();
                areaCnt = result.get(area);
                if(areaCnt == null){
                    areaCnt=0;
                }
                areaCnt++;
                result.put(area,areaCnt);
            }
        }
        return result;
    }

    private HashMap<String,Object> parserParentNodeInfo(HashMap<Integer, Users> childAreaId,Users defaultUser) {
        if(childAreaId == null || childAreaId.size()==0){
            return new HashMap<>();
        }
        UserInRequest userInRequest;
        List<Users> childList;
        HashMap<String,Object> result = getDefaultAreaMap();
        result.put("userno",defaultUser.getUserno());
        result.put("username", defaultUser.getUsername());
        result.put("registerTime",DateUtils.formatDate(defaultUser.getRegisttime(),"yyyy-MM-dd"));
        for (Integer area:childAreaId.keySet()){
            userInRequest = new UserInRequest();
            userInRequest.setToppid(childAreaId.get(area).getToppid());
            childList = usersMapper.selectAllChildNodeUser(userInRequest);
            if(area == 1){
                result.put("oneArea",childList.size());
            }else if(area == 2){
                result.put("twoArea",childList.size());
            }else if(area == 3){
                result.put("threeArea",childList.size());
            }else{
                result.put("oneArea",childList.size()+Integer.parseInt(result.get("oneArea")+""));
            }
        }
        return result;
    }

    private HashMap<Integer, Users> parserChildAreaToppid(List<Users> childList) {

        HashMap<Integer,Users> result = new HashMap<>();
        Integer i =0;
        for (Users users:childList){
            if(users.getArea() != -1){
                result.put(users.getArea(),users);
            }else {
                result.put(i+1,users);
                i++;
            }
        }
        return result;
    }

    private Boolean invalidParams(Users request, UserResponse userResponse) {

        if(StringUtils.isEmpty(request.getUserno())){
            return true;
        }

        Users users = usersMapper.selectByUserNo(request.getUserno());
        if(users == null ){
            return false;
        }
        return invalidChildNode(users.getUserid(),userResponse.getToppid(),userResponse.getUserId());
    }

    private void parserTwoChildList(List<HashMap<String, Object>> areaTwoList, TempChildList tempChildList) {

        HashMap<Long,List<Users>> adminChildMap = tempChildList.getAdminChildMap();

        if(adminChildMap.size()!=0){
            HashMap<Long,Users> userIndexMap = tempChildList.getUserIndexMap();
            for (Long userId:adminChildMap.keySet()){
                areaTwoList.add(parserAreaUserInfo(adminChildMap.get(userId),userIndexMap.get(userId)));
            }
        }else{
            if(tempChildList.getOneUser()!=null)
                areaTwoList.add(parserAreaUserInfo(tempChildList.getOneAreaList(),tempChildList.getOneUser()));
            if(tempChildList.getTwoUser()!=null)
                areaTwoList.add(parserAreaUserInfo(tempChildList.getTwoAreaList(),tempChildList.getTwoUser()));
        }
    }

    private Users getParentUserMode(UserResponse userResponse) {
        Users users = new Users();
        users.setUserno(userResponse.getUserNo());
        users.setRegisttime(userResponse.getRegisterTime());
        users.setUsername(userResponse.getUsername());
        return users;
    }

    private TempChildList parserAreaChildUserInfo(List<Users> childList) {

        TempChildList result = new TempChildList();
        Integer area;
        List<Users> childNodeList;
        for (Users users:childList){
            area = users.getArea();
            childNodeList = getChildList(users.getToppid(),users.getUserid());
            if(area ==1){
                result.setOneAreaList(childNodeList);
                result.setOneUser(users);
            }else if(area ==2) {
                result.setTwoAreaList(childNodeList);
                result.setTwoUser(users);
            }else if(area == -1){
                result.getAdminChildMap().put(users.getUserid(),childNodeList);
            }
            result.getUserIndexMap().put(users.getUserid(),users);
        }
        result.setAllUsers(childList);
        return result;
    }

    private List<Users> getChildList(String toppid,Long userid){
        UserInRequest inRequest = new UserInRequest();
        inRequest.setToppid(toppid);
        //统计时 排除的用户Id
        inRequest.setUserid(userid);
        List<Users> result = usersMapper.selectAllChildNodeUser(inRequest);
        if(result == null){
            result = new ArrayList<>();
        }
        return result;
    }


    /**
     * 1 获取当前管理员下所有的会员
     * 2 分区 每个会员下对应的会员信息
     * 3 从第一层 搜索的会员名对应的Id  获取下面每层的会员信息
     */
    /*public BaseResult getOldUserList(QueryUserListRequest request, UserResponse userResponse) {
        if (userResponse == null || userResponse.getUserId() == null) {
            return new BaseResult(ExceptionEnum.TOKENRERROR);
        }

        //获取当前登陆用户下所有的会员
        List<Users> allUsers = usersMapper.selectByTopId(userResponse.getUserId());
        if (allUsers == null || allUsers.isEmpty()) {
            return new BaseResult(new Object());
        }


        Long queryId = getQueryId(request, userResponse);
        if (queryId == -1L) {
            return new BaseResult(new ArrayList<>());
        }
        Long startTime = System.currentTimeMillis();
        // 获取每个节点下的子节点 (索引表)
        HashMap<Long, Set<Long>> indexMap = parserNodeUserMap(allUsers);
        //获取每个用户的详细信息 (查询表)
        HashMap<Long, Users> allUserInfo = parserAllSingleUserInfo(allUsers);
        //通过查找的用户id为顶层节点 遍历索引表 获取所有的用户id
        Set<Long> allUserId = traverseTreeByAllNodeId(indexMap, queryId);
        //检索查询表 获得最终信息
        List<HashMap<String, Object>> usersData = getAllUserInfo(allUserId, allUserInfo, indexMap, request);
        System.out.println("【计算树形节点耗时：】" + (System.currentTimeMillis() - startTime));
        if (usersData == null) {
            return new BaseResult(new ArrayList<>());
        }
        HashMap<String, Object> resData = new HashMap<>();
        resData.put("total", usersData.size());
        //排序
        commonUtil.doSortList(usersData, "desc", "registerTime");
        //分页
        resData.put("data", commonUtil.getPagingList(usersData, request.getPage(), request.getPageSize()));
        resData.put("page", request.getPage());
        resData.put("pageSize", request.getPageSize());
        return new BaseResult(resData);
    }

    private Long getQueryId(QueryUserListRequest request, UserResponse userResponse) {
        Long queryId = null;
        //不存在输入搜索用户时  默认搜索登陆用户自己
        if (request.getUserno() == null || request.getUserno().equals("")) {
            queryId = userResponse.getUserId();
        } else {
            //获取搜索的用户id
          *//*  Users queryUsers = usersMapper.selectSearchQuery(request);
            if (queryUsers == null) {
                return -1L;
            }
            queryId = queryUsers.getUserid();*//*
        }
        return queryId;
    }*/

   /* public BaseResult getOldUserTree(Users request, UserResponse userResponse) {

//        //获取搜索的会员id
//        Users queryUser = usersMapper.selectByUserNo(request.getUserno());
//        if(queryUser == null){
//            return new BaseResult(ExceptionEnum.ERRUSERID);
//        }
        //获取当前登陆用户下所有的会员
        List<Users> allUsers = usersMapper.selectByTopId(userResponse.getUserId());

        *//**
         * 1 获取当前搜索用户下所有下线
         * 2 以当前用户id为搜索节点 遍历属性图获取第一层需要的1区2区人数
         * 3 获取2个直接下线
         * 3 根据这2个直接下线 遍历各自下所有的下线人数
         *//*
        // 获取每个节点下的子节点 (索引表)
        HashMap<Long, Set<Long>> indexMap = parserNodeUserMap(allUsers);
        //获取每个用户的详细信息 (查询表)
        HashMap<Long, Users> allUserInfo = parserAllSingleUserInfo(allUsers);

        //组装查询条件
        QueryUserListRequest queryList = getQueryList(request);
        //获取查询节点id  这里也是父级节点 userId
        Long queryId = getQueryId(queryList, userResponse);
        //获取顶层节点的详细信息
        Users parentUsers = usersMapper.selectByPrimaryKey(queryId);
        //获取当前节点直系的2个节点
        List<Users> childNode = usersMapper.selectByPid(queryId);

        //节点区域表  这儿是获取一区和二区各自对应的userId
        HashMap<Long, Integer> areaIndexMap = parserIdNodeMap(childNode);

        Set<Long> chileNodeId = getChildNodeId(childNode);

        //通过算法获取每个节点下的人数
        //通过查找的用户id为顶层节点 遍历索引表 获取所有的用户id
        HashMap<Long, Set<Long>> childNodeAllCnt = new HashMap<>();
        for (Long childId : chileNodeId) {
            Set<Long> allUserId = traverseTreeByAllNodeId(indexMap, childId);
            childNodeAllCnt.put(childId, allUserId);
        }
        //获取父亲节点下所有的成员信息
        Set<Long> rankingNodeAllcnt = traverseTreeByAllNodeId(indexMap, queryId);

        //初始化返回值结构
        HashMap<String, Object> resultMap = initReturnDefaultMap();

        //获取每个节点下的用户详细信息列表
        //父亲节点
        List<Users> rankingAllUserList = getNodeDetailLst(rankingNodeAllcnt, allUserInfo);
        //获取子节点的用户相信信息列表
        HashMap<Integer, List<Users>> userAreaMap = parserAreaUserMap(childNodeAllCnt, areaIndexMap, allUserInfo);
        //获取父亲节点下 每个区域的人数
        resultMap.put("level_one",parserAreaUserInfo(rankingAllUserList,parentUsers));
        //节点 一区 下面每个区域的人数
        HashMap<String,Object> oneAreaUserMap = parserAreaUserInfo(userAreaMap.get(1),allUserInfo.get(getAreaUserId(areaIndexMap,1)));
        //节点 二区 下面每个区域的人数
        HashMap<String,Object> twoAreaUserMap = parserAreaUserInfo(userAreaMap.get(2),allUserInfo.get(getAreaUserId(areaIndexMap,2)));
        List<HashMap<String, Object>> childList = new ArrayList<>();
        childList.add(oneAreaUserMap);
        childList.add(twoAreaUserMap);
        resultMap.put("level_two", childList);
        return new BaseResult(resultMap);
    }*/

    private Long getAreaUserId(HashMap<Long,Integer> areaMap,Integer area){
        if(areaMap==null){
            return null;
        }
        for (Long l:areaMap.keySet()){
            if(areaMap.get(l).equals(area)){
                return l;
            }
        }
        return null;
    }

    @Override
    public BaseResult getSingleUserInfo(SingleUserRequest request, UserResponse userResponse) {
        if (request == null) {
            return new BaseResult(ExceptionEnum.PARAMEMPTYPEROR);
        }
        HashMap<String, Object> resultMap = new HashMap<>();
        //组装查询条件
        QueryUserListRequest userListRequest = new QueryUserListRequest();

        //获取推荐人信息
        if(!StringUtils.isEmpty(request.getPerUserNo())){
            Users parentUser = usersMapper.selectByUserNo(request.getPerUserNo());
            if(parentUser == null){
                return new BaseResult(resultMap);
            }
            userListRequest.setPid(parentUser.getUserid()+"");
        }

        userListRequest.setUserno(request.getUserno());
        userListRequest.setToppid(userResponse.getToppid());
        userListRequest.setUsername(request.getUsername());
        userListRequest.setLevelid(request.getLevelid());
        Integer allRowsTotal = usersMapper.selectSearchQueryCnt(userListRequest);
        userListRequest.setPage((request.getPage()-1)*request.getPageSize());
        userListRequest.setPageSize(request.getPage()*request.getPageSize());

        List<Users> usersList;
        try {
            usersList = usersMapper.selectSearchQuery(userListRequest);
        } catch (Exception e) {
            logger.error("【查询用户信息修改列表接口报错:e:】" + e.getMessage());
            return new BaseResult(ExceptionEnum.DATABASEERROR);
        }
        List<HashMap<String, Object>> resultList = packageUserMap(usersList);
        resultMap.put("total", allRowsTotal);
        resultMap.put("data", resultList);
        resultMap.put("page", request.getPage());
        resultMap.put("pageSize", request.getPageSize());
        return new BaseResult(resultMap);
    }

    @Override
    public BaseResult updateLoginUser(UpdateModel request, UserResponse userResponse) {

        if (userResponse == null || userResponse.getUserId() == null) {
            return new BaseResult(ExceptionEnum.TOKENRERROR);
        }
        if (request.getAuthorizationCode() == null) {
            logger.error("【更改会员信息时,授权码为空,中断此次操作】");
            return new BaseResult(ExceptionEnum.AUTHCODE);
        }
        Long userId = userResponse.getUserId();
        String key = JedisKey.accreditcodeKey(userId);
        //验证授权码
        String authCode = redisClient.get(key);
//        redisClient.del(key);
        if (!request.getAuthorizationCode().equals(authCode)) {
            logger.error("【更改会员信息时,授权码验证失败,中断此次操作】");
            return new BaseResult(ExceptionEnum.AUTHCODE);
        }
        Users users = getUpdateUserModel(request, userId);
        //通过验证 更改信息
        if (users != null) {
            updateUserInfo(users);
        }
        UserBankcords userBankcords = getUpdateBankcordsModel(request, userId);
        if (userBankcords != null) {
            userBankcordsMapper.updateByPrimaryKeySelective(userBankcords);
        }

        return new BaseResult(ExceptionEnum.SUCCESS);
    }

    private UserBankcords getUpdateBankcordsModel(UpdateModel request, Long userId) {
        UserBankcords userBankcords = new UserBankcords();
        userBankcords.setUserid(userId);
        if(request.getBankno() == null || request.getBankno().equals("")){
            return null;
        }
        userBankcords.setBankno(Integer.parseInt(request.getBankno()));
        return userBankcords;
    }

    private Users getUpdateUserModel(UpdateModel request, Long userId) {
        Boolean flag = false;
        Users users = new Users();
        if (!StringUtils.isEmpty(request.getPassword())) {
            users.setPwd(request.getPassword());
            flag = true;
        }

        if (!StringUtils.isEmpty(request.getPayPassword())) {
            users.setPaypwd(request.getPayPassword());
            flag = true;
        }

        if(request.getTleno() != null ){
            users.setTleno(request.getTleno()+"");
            flag = true;
        }

        if(!StringUtils.isEmpty(request.getAddress())){
            users.setAddress(request.getAddress());
            flag = true;
        }

        if (!flag) {
            return null;
        }
        users.setUserid(userId);
        return users;
    }

    @Override
    public BaseResult invalidPassword(UpdateModel request, UserResponse userResponse) {
        if (request == null || request.getPassword() == null || request.getPassword().equals("")) {
            return new BaseResult(ExceptionEnum.PARAMEMPTYPEROR);
        }
        //获取用户原始密码
        Users users = usersMapper.selectByPrimaryKey(userResponse.getUserId());
        if (users == null) {
            return new BaseResult(ExceptionEnum.ERRUSERID);
        }
        if (!request.getPassword().equals(users.getPwd())) {
            return new BaseResult(ExceptionEnum.PASSWORDINVALID);
        }

        //生成随机授权码
        String authCode = RandomUtils.getRandomString(18);
        //缓存至redis 授权码有效期 10分钟
        redisClient.setex(JedisKey.accreditcodeKey(userResponse.getUserId()), authCode, JedisKey.TEN_MINUTE);
        HashMap<String,String> result = new HashMap<>();
        result.put("authorizationCode",authCode);
        return new BaseResult(result);
    }

    @Override
    public Double getAmount(Users user, Date begin, Date end) {
        // 查询某用户的日工资记录
        UserAmountDetail scope = new UserAmountDetail();
        scope.setMinDate(begin);
        scope.setMaxDate(end);
        scope.setUserid(user.getUserid());
        List<UserAmountDetail> list = userAmountDetailMapper.selectDetail(scope);
        BigDecimal result = new BigDecimal(0.00);
        for(int i=0;i<list.size();i++){
            Float amount = list.get(i).getAmount();
            result = result.add(new BigDecimal(amount.toString()));
        }
        return result.doubleValue();
    }

    @Override
    public BaseResult authority(Users users) {
        if(users == null || users.getUserid()==null){
            return new BaseResult(ExceptionEnum.PARAMEMPTYPEROR);
        }
        users.setPerid(Long.parseLong(PermissionEnum.ADMIN.getPre_level()+""));
        try {

            usersMapper.updateByPrimaryKeySelective(users);
        } catch (Exception e) {
            logger.error("【授权用户发生异常 e:】"+e.getMessage());
            return new BaseResult(ExceptionEnum.DATABASEERROR);
        }
        return new BaseResult(ExceptionEnum.SUCCESS);
    }

    private String fetchAdminParentNodeId() {

        String uuid = RandomUtils.getRandLetter(8);
        Jedis jedis = null;
        try {
            jedis = redisClient.getJedis(JedisKey.NODEIDID);
            while (true){
                if(jedis.sismember(JedisKey.NODENAME,uuid)){
                    uuid = RandomUtils.getRandom(8);
                }else{
                    break;
                }
            }
            jedis.sadd(JedisKey.NODENAME,uuid);
        } catch (Exception e) {
           logger.error("【获取基点字符串失败 e:】"+e.getMessage());
        } finally {
            redisClient.release(jedis,false);
        }

        return buildNodeId(uuid);
    }

    public static String buildNodeId(String key1,String...keys){
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append(key1).append("|");
        for (Object obj : keys) {
            stringBuffer.append(obj).append("|");
        }
        return stringBuffer.toString();
    }

    private List<HashMap<String, Object>> packageUserMap(List<Users> usersList) {
        List<HashMap<String, Object>> result = new ArrayList<>();

        if (usersList == null) {
            return result;
        }
        List<Long> preUserIdList = new ArrayList<>();
        HashMap<String, Object> itemMap;
        for (Users users : usersList) {
            itemMap = new HashMap<>();
            itemMap.put("userno", users.getUserno());
            itemMap.put("username", users.getUsername());
            itemMap.put("pid", users.getPid());
            itemMap.put("telephone", users.getTleno());
            itemMap.put("authority", judgeAuthority(users.getPerid()));
            itemMap.put("levelDesc", AutoAccountingJob.getGlobalLevelMap().get(users.getLevelid()).getLevelname());
            itemMap.put("levelId", users.getLevelid());
            itemMap.put("area", users.getArea());
            itemMap.put("userid",users.getUserid());
            result.add(itemMap);
            if(users.getPid() != null)
                preUserIdList.add(users.getPid());
        }
        if(preUserIdList.size()==0){
            return result;
        }
        //获取引荐人表
        List<Users> preUserList = usersMapper.selectUserListByUserIds(preUserIdList);
        HashMap<String,Users> preUserMap = transListToMap(preUserList);
        Object pid;
        //填充引荐人昵称属性
        for (HashMap<String,Object> userMap:result){
            pid = userMap.get("pid");
            if(pid == null){
                continue;
            }
            userMap.put("perUserno",preUserMap.get(pid+"").getUserno());
        }
        return result;
    }

    private HashMap<String, Users> transListToMap(List<Users> preUserIdList) {

        HashMap<String,Users> result = new HashMap<>();
        if(preUserIdList.size()==0){
            return result;
        }
        for (Users users:preUserIdList){
            result.put(users.getUserid()+"",users);
        }
        return result;
    }

    private Integer judgeAuthority(Long perId) {
        if (perId <= PermissionEnum.find(Const.ADMIN, PermissionEnum.EMPLOYEE).getPre_level()) {
            return 1;
        } else {
            return 0;
        }
    }

    private HashMap<Integer, List<Users>> parserAreaUserMap(HashMap<Long, Set<Long>> childNodeAllCnt,
                                                            HashMap<Long, Integer> areaIndexMap, HashMap<Long, Users> allUserInfo) {

        HashMap<Integer, List<Users>> result = new HashMap<>();

        for (Long userId : childNodeAllCnt.keySet()) {
            result.put(areaIndexMap.get(userId), getNodeDetailLst(childNodeAllCnt.get(userId), allUserInfo));
        }
        return result;
    }

    private HashMap<Long, Integer> parserIdNodeMap(List<Users> childNode) {
        HashMap<Long, Integer> result = new HashMap<>();
        if (childNode == null) {
            return result;
        }
        for (Users users : childNode) {
            result.put(users.getUserid(), users.getArea());
        }
        return result;
    }

    private List<Users> getNodeDetailLst(Set<Long> rankingNodeAllcnt, HashMap<Long, Users> allUserInfo) {

        List<Users> result = new ArrayList<>();
        if (rankingNodeAllcnt == null || rankingNodeAllcnt.isEmpty()) {
            return result;
        }
        for (Long userId : rankingNodeAllcnt) {
            result.add(allUserInfo.get(userId));
        }
        return result;
    }

    private HashMap<String, Object> initReturnDefaultMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("level_one", new HashMap<>());
        result.put("level_two", new ArrayList<>());
        return result;
    }

    private Set<Long> getChildNodeId(List<Users> childNode) {
        Set<Long> result = new HashSet<>();

        if (childNode == null || childNode.isEmpty()) {
            return result;
        }
        for (Users users : childNode) {
            result.add(users.getUserid());
        }
        return result;
    }

    private QueryUserListRequest getQueryList(Users request) {
        QueryUserListRequest result = new QueryUserListRequest();
        result.setUserno(request.getUserno());
        request.setUsername(request.getUsername());
        request.setTleno(request.getTleno());
        return result;
    }

    private boolean filterData(Users users, QueryUserListRequest request) {

        if(StringUtils.isEmpty(request.getStartTime()) && StringUtils.isEmpty(request.getEndTime())){
            return true;
        }


        Date date = users.getRegisttime();
        if (date == null) {
            return false;
        }
        String dateStr = DateUtils.formatDate(date, DateUtils.DATE_FORMAT_YYYY_MM_DD);
        if (dateStr == null || dateStr.isEmpty()) {
            return false;
        }
        Long startTime = Long.parseLong(request.getStartTime().replaceAll("/", ""));
        Long endTime = Long.parseLong(request.getEndTime().replaceAll("/", ""));
        Long nowTime = Long.parseLong(dateStr.replaceAll("-", "").replaceAll(" ", ""));

        if(StringUtils.isEmpty(request.getStartTime()) && !StringUtils.isEmpty(request.getEndTime())){
            return nowTime <= endTime;
        }

        if(StringUtils.isEmpty(request.getEndTime()) && !StringUtils.isEmpty(request.getStartTime())){
            return nowTime >= startTime;
        }

        return nowTime <= endTime && nowTime >= startTime;
    }

    private List<HashMap<String, Object>> getAllUserInfo(Set<Long> allUserId, HashMap<Long, Users> allUserInfo, HashMap<Long, Set<Long>> indexMap, QueryUserListRequest request) {
        List<HashMap<String, Object>> result = new ArrayList<>();

        HashMap<String, Object> itemMap;
        Users users;
        Set<Long> userBelowSet;
        for (Long userId : allUserId) {
            users = allUserInfo.get(userId);
            if (users == null) {
                continue;
            }
            //根据时间节点筛选
            if (!filterData(users, request)) {
                continue;
            }
            userBelowSet = indexMap.get(userId);
            itemMap = getItemMap(users);
            itemMap.put("recommendCnt", userBelowSet == null ? 0 : userBelowSet.size());
            result.add(itemMap);
        }
        return result;
    }

    private HashMap<String,Object> getItemMap(Users users){
        HashMap<String,Object> itemMap = new HashMap<>();
        itemMap.put("userno", users.getUserno());
        itemMap.put("username", users.getUsername());
        itemMap.put("telephone", users.getTleno());
        itemMap.put("registerTime", DateUtils.formatDate(users.getRegisttime(), DateUtils.DATE_FORMAT_YYYY_MM_DD));
        itemMap.put("area", users.getArea());
        itemMap.put("idcard",users.getIdcord());
        itemMap.put("preUserNo",usersMapper.selectByPrimaryKey(users.getPid()).getUserno());
        return itemMap;
    }

    public HashMap<Long, Users> parserAllSingleUserInfo(List<Users> allUsers) {
        HashMap<Long, Users> result = new HashMap<>();
        for (Users users : allUsers) {
            result.put(users.getUserid(), users);
        }
        return result;
    }

    public HashMap<Integer,Set<Long>> traverseTreeByAllNodeId(HashMap<Long, Set<Long>> indexMap, Long userid) {
        HashMap<Integer,Set<Long>> areaUserIdMap = getDefaultMap();
        //获取基址
        Set<Long> baseNodeSet = indexMap.get(userid);
        if (baseNodeSet == null || baseNodeSet.size() == 0) {
            return areaUserIdMap;
        }
        //设计一个线程安全的链表 用于遍历时 动态改变队列元素
        ConcurrentLinkedDeque<Long> outputIdQueue = new ConcurrentLinkedDeque<>();

        // 二叉树遍历算法变异版 通过java提供的线程安全的链表实现深度优先的遍历机制 遍历树形图节点 拿到所有符合条件的节点id
        /**
         * 舍去了广度优先的2层循环缺点 使用任务池的方式实现 0(1)复杂度
         */
        Integer i = 1;
        Set<Long> userIdSet;
        for (Long id:baseNodeSet){
            outputIdQueue.clear();
            outputIdQueue.add(id);
            while (!outputIdQueue.isEmpty()) {
                Long nodeId = outputIdQueue.peekFirst();
                userIdSet = areaUserIdMap.get(i);
                if(userIdSet == null){
                    userIdSet = new HashSet<>();
                }
                userIdSet.add(nodeId);
                logger.debug("开始遍历userId：" + nodeId + " 下的节点");
                //获取到第几N层子节点 放入链表 继续扫描
                Set<Long> tempSet = indexMap.get(nodeId);
                if (tempSet == null) {
                    logger.debug("遍历至userId:" + nodeId + " 时,检测到无子节点,跳过这次检测");
                    outputIdQueue.remove(nodeId);
                    continue;
                }
                //把当前节点信息放入总集合
                userIdSet.addAll(tempSet);
                //将当前子节点放入链表中 等待扫描
                outputIdQueue.addAll(tempSet);
                //删除已经被扫描的节点
                outputIdQueue.remove(nodeId);
            }
            i++;
        }
        return areaUserIdMap;
    }

    private HashMap<Integer,Set<Long>> getDefaultMap(){
        HashMap<Integer,Set<Long>> result = new HashMap<>();
        result.put(1,new HashSet<>());
        result.put(2,new HashSet<>());
        result.put(3,new HashSet<>());
        return result;
    }

    /**
     * 创建索引表 每个用户下所有的直接下线
     */
    public HashMap<Long, Set<Long>> parserNodeUserMap(List<Users> allUsers) {

        HashMap<Long, Set<Long>> result = new HashMap<>();
        Set<Long> indexTopIdSet;
        Long userId, pid;
        for (Users users : allUsers) {
            userId = users.getUserid();
            pid = users.getPid();
            indexTopIdSet = result.get(pid);

            if (indexTopIdSet == null) {
                indexTopIdSet = new HashSet<>();
            }
            indexTopIdSet.add(userId);
            result.put(pid, indexTopIdSet);
        }
        return result;
    }

    private Users getUser(Long userId) {
        return usersMapper.selectByPrimaryKey(userId);
    }

    private HashMap<String, Object> packageResponseModel(Users users, Long role) {
        HashMap<String, Object> result = new HashMap<>();

        result.put("username", users.getUsername());
        result.put("userno", users.getUserno());
        result.put("pid", users.getPid());
        result.put("area", users.getArea());
        result.put("userid", users.getUserid());
        result.put("level", UserLevelEnum.getAmountBylevelCode(users.getLevelid()).getLevelName());
        result.put("registerTime", commonUtil.invalidObject(DateUtils.formatDate(users.getRegisttime(),DateUtils.DATE_FORMAT_YYYY_MM_DD)));
        result.put("telephone", commonUtil.invalidObject(users.getTleno()));
        result.put("idcord", commonUtil.invalidObject(users.getIdcord()));
        result.put("address", commonUtil.invalidObject(users.getAddress()));
        result.put("perUserno",users.getPerUserNo());
        result.put("meet",false);
        if (role <= PermissionEnum.find(Const.ADMIN, PermissionEnum.EMPLOYEE).getPre_level()) {
            //管理员级别返回密码字段
            result.put("pwd", users.getPwd());
            result.put("paypwd", users.getPaypwd());
        }
        return result;
    }

    private Boolean checkParams(Users users) {
        return users.getUsername() != null &&
                users.getUserno() != null &&
                users.getPerUserNo() != null &&
                users.getPaypwd() != null &&
                users.getPwd() != null &&
                users.getArea() != null;
    }
}
