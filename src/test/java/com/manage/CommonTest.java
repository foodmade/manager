package com.manage;

import com.alibaba.fastjson.JSON;
import com.manage.common.commonUtil.CommonUtil;
import com.manage.controller.QryDataController;
import com.manage.entity.Droa;
import com.manage.vo.UserScope;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommonTest extends BaseTest{

    @Autowired
    private QryDataController qryDataController;

    public static void main(String[] args) throws Exception {

        HashMap<String,Object> droaMap = new HashMap<>();
        droaMap.put("userid",1L);
        Droa droa = JSON.parseObject(JSON.toJSONString(droaMap),Droa.class);
        List<Droa> list = new ArrayList<>();
        list.add(droa);


        String s = "{\"Applytime\":\"\",\"Crod\":\"\",\"Userid\":1,\"State\":\"\",\"Exetime\":\"\",\"Exeuserid\":\"\",\"Droaid\":\"\",\"Userno\":\"\",\"Droaamount\":\"\",\"Balance\":\"\"}";

        System.out.println(JSON.toJSONString(JSON.parseObject(s,Droa.class)));
//        List<Droa> droaList = new ArrayList<>();
//
//        for (int i=0;i<3;i++){
//            Droa droa = new Droa();
//            droa.setExetime(null);
//            droa.setState(1);
//            droa.setApplytime(null);
//            droa.setBalance(0.12D);
//            droa.setDroaid(1L);
//            droa.setUserid(1L);
//            droaList.add(droa);
//        }
//        System.out.println(JSON.toJSONString(CommonUtil.transferFormat(droaList)));
    }

    @Test
    public void testQryGetStatement(){
        UserScope request = new UserScope();
        System.out.println(JSON.toJSONString(qryDataController.getStatement(request)));

    }



}
