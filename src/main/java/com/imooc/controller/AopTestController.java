package com.imooc.controller;

import com.imooc.service.AopService;
import com.imooc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by hukai on 2018/7/21.
 */

@Controller
public class AopTestController {

    @Autowired
    private AopService aopService;

    @Autowired
    private UserService userService;

    @RequestMapping("aop")
    @ResponseBody
    public Object aop() {

        aopService.doAop();

        return "hello,world";
    }

    @RequestMapping("tran")
    @ResponseBody
    public Object testTransaction() {
        userService.insertUserGood();
        return "hello";
    }

    @RequestMapping("read")
    @ResponseBody
    public Object testMultiRead(){
        userService.readMultiTimes();
        return "hello";
    }
}
