package com.manage.controller;

import com.manage.annotation.BaseChek;
import com.manage.common.Const;
import com.manage.entity.Users;
import com.manage.service.TestService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Properties;

@Controller
public class TestController {

    Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private Properties commonProperties;

    @Autowired
    private TestService testService;

//    @ResponseBody
//    @RequestMapping(value = "/test.do",method = RequestMethod.POST)
//    @BaseChek(needLogin = false, needCheckParameter = true, beanClazz = Users.class)
//    public HashMap<String,Object> index(@RequestAttribute  User request){
//        HashMap<String,Object> result = new HashMap<>();
//        System.out.println("user:"+ request.toString());
//        result.put("success",true);
//        result.put("error","111");
//        result.put("properties",commonProperties.get("profile"));
//        return result;
//    }

    @RequestMapping("/testMysql.do")
    @ResponseBody
    @BaseChek(needLogin = true,permissionFilter = Const.ADMIN,needCheckParameter = false)
    public HashMap<String,Object> testMysql(){
        logger.info("testMysql..........");
        HashMap<String,Object> result = new HashMap<>();
        result.put("body",testService.getUserInfo(461854083565924352L));
        return result;
    }

    @RequestMapping("/main.do")
    public String homepage(){
        return "main";
    }

    @RequestMapping("/error.do")
    public String err(){
        return "404";
    }



}
