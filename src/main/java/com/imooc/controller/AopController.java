package com.imooc.controller;

import com.imooc.service.AopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by hukai on 2018/7/21.
 */

@Controller
public class AopController {

    @Autowired
    private AopService aopService;

    @RequestMapping("aop")
    @ResponseBody
    public Object aop() {

        aopService.doAop();

        return "hello,world";
    }
}
