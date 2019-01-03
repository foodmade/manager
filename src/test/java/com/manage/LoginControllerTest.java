package com.manage;

import com.manage.controller.TestController;
import org.eclipse.jetty.util.ajax.JSON;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

public class LoginControllerTest extends BaseTest {

    @Autowired
    TestController testController;

    @Test
    public void test(){
        System.out.println(JSON.toString(testController.testMysql()));
    }
}
