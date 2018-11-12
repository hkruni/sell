package com.imooc.controller;

import org.springframework.jdbc.support.incrementer.SybaseAnywhereMaxValueIncrementer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 测试threadlocal
 */
@Controller
public class ThreadLocalController {

    ThreadLocal<Integer> localStr = new ThreadLocal<>();

    private static Integer s = 0;

    @GetMapping("/test")
    @ResponseBody
    public Object testThreadLocal() {

        System.out.println(Thread.currentThread().getId() + "::" + Thread.currentThread().getName());
        System.out.println("localStr.get() : " +localStr.get());
        localStr.set(s);
        System.out.println("localStr.get2() :" +localStr.get());

        String s = System.currentTimeMillis() + "";
        return "333";
    }

    @RequestMapping(method= RequestMethod.POST ,value="/register")
    @ResponseBody
    public Map testPost(@RequestParam("name") String name,@RequestParam("password") String password) {
        Map<String ,Object> map = new HashMap<>();
        map.put("status","OK");
        map.put("data","this is result");
        System.out.println("name : " + name + " password: " + password);
        return map;
    }
}
