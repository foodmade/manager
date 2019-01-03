package com.manage;

import com.alibaba.fastjson.JSON;
import com.manage.common.commonUtil.DateUtil;
import com.manage.common.commonUtil.DateUtils;
import com.manage.controller.QryDataController;
import com.manage.controller.UserController;
import com.manage.dao.UserAmountDetailMapper;
import com.manage.dao.UsersMapper;
import com.manage.entity.UserAmountDetail;
import com.manage.entity.UserResponse;
import com.manage.entity.Users;
import com.manage.job.UpdateUserLevelJob;
import com.manage.vo.QueryUserListRequest;
import com.manage.vo.SingleUserRequest;
import com.manage.vo.UpdateModel;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class UserControllerTest extends BaseTest {

    @Autowired
    private UserController userController;
    @Autowired
    private UserAmountDetailMapper userAmountDetailMapper;
    @Autowired
    private UpdateUserLevelJob updateUserLevelJob;
    @Autowired
    private QryDataController qryDataController;

    @Autowired
    private UsersMapper usersMapper;
    private static Users users = new Users();
    private static UserResponse userResponse = new UserResponse();

    static{
        users.setArea(1);
        users.setPid(1L);
        users.setPaypwd("250250");
        users.setPwd("chen1996");
        users.setUsername("陈小明_");
        users.setUserno("chen");
        users.setAddress("成都市高新区");
        users.setPerid(1L);
        users.setIdcord("511623199601197670");
        users.setLevelid(2L);
//        users.setUserid(2L);

        userResponse.setUserNo("jackies");
        userResponse.setRole(2L);
        userResponse.setUserId(6L);
    }

    @Test
    public void testAddUser(){
//        System.out.println(JSON.toJSONString(userController.addUser(users,userResponse)));
        List<Users> lists = new ArrayList<>();
        Users users ;
        for (int i =0;i<50;i++){
            users = new Users();
            users.setArea(1);
            users.setPid(1L);
            users.setPaypwd("250250");
            users.setPwd("chen19960119"+i);
            users.setUsername("陈小明_"+i);
            users.setUserno("chen");
            users.setAddress("成都市高新区");
            users.setPerid(1L);
            users.setIdcord("511623199601197670"+i);
            users.setLevelid(2L);
            users.setToppid("");
            users.setArea(1);
            users.setState(0);
            users.setHeadiconurl("111");
            users.setTleno("18224449005");
            users.setIsolduser(0);
            users.setRegisttime(new Date());
            lists.add(users);
        }
        usersMapper.insertBatch(lists);
    }

    @Test
    public void testUpdateUser(){
        System.out.println(JSON.toJSONString(userController.updateUser(users)));
    }

    @Test
    public void testCheckUserNo(){
        System.out.println(JSON.toJSONString(userController.checkUserName(users)));
    }

    @Test
    public void testGetUserInfo(){
        QueryUserListRequest request = new QueryUserListRequest();
//        request.setUserno("test");
        request.setPage(1);
        request.setPageSize(20);
        request.setStartTime("2018-07-29");
        request.setEndTime("2018-08-03");
        UserResponse userResponse = new UserResponse();
        userResponse.setUserNo("chen");
        userResponse.setUserId(2071L);
        userResponse.setToppid("StDOcNzV|");
        System.out.println(JSON.toJSONString(userController.getUserList(request,userResponse)));
    }

    @Test
    public void testGetBalanceInfo(){
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(2L);
        System.out.println(JSON.toJSONString(userController.getBalanceInfo(userResponse)));
    }

    @Test
    public void testGetUserTree(){
        Users users = new Users();
        users.setUserid(1L);
        users.setUserno("kang");

        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(1L);
        System.out.println(JSON.toJSONString(userController.getUserTree(users,userResponse)));
    }

    @Test
    public void testInsertAmountDetail(){
        UserAmountDetail userAmountDetail = new UserAmountDetail();
        userAmountDetail.setTime(new Date());
        userAmountDetail.setUserid(1L);
        userAmountDetail.setAmount(432.98f);
        userAmountDetail.setComamount(3.2f);
        userAmountDetail.setTax(0.2f);
        userAmountDetail.setRelamount(430.23f);
        userAmountDetail.setRepeatamount(3.2f);
        for (Integer i=0;i<100;i++){
            userAmountDetailMapper.insert(userAmountDetail);
        }
    }

    @Test
    public void testGetSingleUserInfo(){
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(2L);

        SingleUserRequest request = new SingleUserRequest();
        request.setPage(1);
        request.setPageSize(20);

        System.out.println(JSON.toJSONString(userController.getSingleUserInfo(request,userResponse)));
    }

    @Test
    public void testUpdateChildUserInfo(){
        Users users = new Users();
        users.setUserid(2097L);
        users.setUsername("胀死");
        users.setAddress("者综合镇");

        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(2096L);
        userResponse.setToppid("YWRtaW4=|BBTvaHQQ||1|");
        System.out.println(JSON.toJSONString(userController.updateChildUserInfo(users,userResponse)));
    }

    @Test
    public void testInitAdminUser(){
        userController.initAdminUser(null,"initAdminAccountPwd");
    }

    public static void main(String[] args) throws Exception {
        String startTime = "2018-01-01";
        String endTime = "2018-12-31";
        System.out.println(DateUtils.getLastDayOfMonth(2018,6));
//        System.out.println(StringUtils.isBlank("null"));
    }

    @Test
    public void testInitUserLevel(){
        updateUserLevelJob.updateUserLevel();
    }

    @Test
    public void testGetUserInfoById(){
        Users request = new Users();
        request.setUserid(2107L);

        UserResponse userResponse = new UserResponse();
        userResponse.setRole(1L);

        System.out.println(JSON.toJSONString(userController.getUserInfoById(request,userResponse)));
    }

    @Test
    public void testupdateLoginUser(){
        UpdateModel request = new UpdateModel();

        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(2120l);
        userController.updateLoginUser(request,userResponse);
    }

}
