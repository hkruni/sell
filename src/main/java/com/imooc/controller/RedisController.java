package com.imooc.controller;

import com.imooc.object.Student;
import com.imooc.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Set;

/**
 * Created by hukai on 2018/7/30.
 */

@Controller
public class RedisController {

    @Autowired
    private RedisService redisService;

    private int x = 0;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @RequestMapping("testTransaction")
    @ResponseBody
    public String  testTransaction() {

        Student student = new Student(1,"aaa",100);

        redisTemplate.multi();
        redisTemplate.opsForValue().set("k1","v1");
        redisTemplate.opsForValue().set("k2","v2");
        redisTemplate.opsForValue().set("k3","v3");
        redisTemplate.opsForValue().increment("k4",10);
        redisTemplate.opsForValue().get("k2");
        redisTemplate.opsForValue().get("k5");
        redisTemplate.opsForValue().set("k6",student);
        redisTemplate.opsForValue().get("k6");

        List<Object> list  = redisTemplate.exec();
        System.out.println("list size ： " + list.size());

        for (Object o : list) {
            System.out.println(o);
        }
        return "success";
    }


    @RequestMapping("testList1")
    @ResponseBody
    public String  testList1() {
        Student s1 = new Student(1,"aaa",100);
        Student s2 = new Student(2,"bbb",200);
        Student s3 = new Student(3,"ccc",300);
        Student s4 = new Student(4,"ccc",300);
        Student s5 = new Student(5,"ccc",300);
        Student s6 = new Student(6,"ccc",300);
        Student s7 = new Student(7,"ccc",300);
        Student s8 = new Student(8,"ccc",300);

        System.out.print(redisTemplate.opsForList().leftPush("Student",s1));
        System.out.print(redisTemplate.opsForList().leftPush("Student",s2));
        System.out.print(redisTemplate.opsForList().leftPush("Student",s3));
        System.out.print(redisTemplate.opsForList().leftPush("Student",s4));
        System.out.print(redisTemplate.opsForList().leftPush("Student",s5));
        System.out.print(redisTemplate.opsForList().leftPush("Student",s6));
        System.out.print(redisTemplate.opsForList().leftPush("Student",s7));
        System.out.println(redisTemplate.opsForList().leftPush("Student",s8));

        Object o = redisTemplate.opsForList().index("Student",2);
        if(o instanceof Student) {
            System.out.println("第二个对象 ：" + o);
        }

        List<Object> list = redisTemplate.opsForList().range("Student",1,3);
        for (Object o1 : list) {
            System.out.println("循环：" + o1);
        }

        System.out.println("rightPop +:" + redisTemplate.opsForList().rightPop("Student"));
        redisTemplate.opsForList().rightPopAndLeftPush("Student","aaa");

        return "testList";
    }

    @RequestMapping("testZsort")
    @ResponseBody
    public String  testZsort() {
        redisTemplate.opsForZSet().add("zset","aa",11.1);//返回true代表插入
        redisTemplate.opsForZSet().add("zset","bb",12.2);
        redisTemplate.opsForZSet().add("zset","cc",13.3);
        redisTemplate.opsForZSet().add("zset","dd",15.5);
        redisTemplate.opsForZSet().add("zset","ee",16.6);
        redisTemplate.opsForZSet().add("zset","ff",17.7);
        redisTemplate.opsForZSet().add("zset","gg",18);
        redisTemplate.opsForZSet().add("zset","hh",20);
        redisTemplate.opsForZSet().add("zset","ii",30);
        redisTemplate.opsForZSet().add("zset","jj",40);

        System.out.println("count : " + redisTemplate.opsForZSet().count("zset",20,50));
        System.out.println("rank : " + redisTemplate.opsForZSet().rank("zset","cc"));
        System.out.println("revrank : " + redisTemplate.opsForZSet().reverseRank("zset","cc"));
        System.out.println("score : " + redisTemplate.opsForZSet().score("zset","cc"));

        Set<Object> set  = redisTemplate.opsForZSet().range("zset",0,4);
        for (Object o : set) {
            System.out.print(o + " ");
        }
        System.out.println();
        Set<ZSetOperations.TypedTuple<Object>> set1 = redisTemplate.opsForZSet().rangeWithScores("zset",0,4);
        for (ZSetOperations.TypedTuple<Object> objectTypedTuple : set1) {
            System.out.print(objectTypedTuple.getValue() + ":" + objectTypedTuple.getScore() + " ");
        }
        System.out.println();
        Set<Object> set3 = redisTemplate.opsForZSet().rangeByScore("zset",20,60);

        for (Object o3 : set3) {
            System.out.print(o3 + " ");
        }
        System.out.println();

        //System.out.println("removeRange : " + redisTemplate.opsForZSet().removeRange("zset",0,3));


        redisTemplate.opsForZSet().add("zset","a1",100);
        redisTemplate.opsForZSet().removeRange("zset",0,-11);//删除0，到第倒数11 之间第元素，只保留最大10个
        Set<Object> set4 = redisTemplate.opsForZSet().range("zset",0,-1);
        for (Object o4 : set4) {
            System.out.print(o4 + " ");
        }
        System.out.println();

        return "success";
    }

    @RequestMapping("testLock")
    @ResponseBody
    public String  testLock() {
        String result = redisService.acquireLockWithTimeout("ll",100000,5000);
        if (result != null) {
            x ++;
            System.out.println(x);
            redisService.releaseLock("ll",result);
        }

        return "success";

    }


    @RequestMapping("testList")
    @ResponseBody
    public String testList() {
        String prefix = "list";
        String key = "user";
        Student s1 = new Student(1,"aaa",100);
        Student s2 = new Student(2,"bbb",200);
        Student s3 = new Student(3,"ccc",300);
        Student s4 = new Student(4,"ccc",300);
        Student s5 = new Student(5,"ccc",300);
        Student s6 = new Student(6,"ccc",300);
        Student s7 = new Student(7,"ccc",300);
        Student s8 = new Student(8,"ccc",300);

        redisService.rpush(prefix,key,s1);
        redisService.rpush(prefix,key,s2);
        redisService.rpush(prefix,key,s3);
        redisService.rpush(prefix,key,s4);
        redisService.rpush(prefix,key,s5);
        redisService.rpush(prefix,key,s6);
        redisService.rpush(prefix,key,s7);
        redisService.rpush(prefix,key,s8);


        Student s = redisService.pop(prefix,key,0,Student.class);
        System.out.println(s);

        List<Student> ss = redisService.range(prefix,key,0,-1,Student.class);
        ss.forEach(System.out::println);

        return  "success";
    }

    @RequestMapping("testzsort")
    @ResponseBody
    public String testzsort() throws InterruptedException {
        long currentTime = System.currentTimeMillis();
        redisService.zadd("zsort","access",currentTime,""+currentTime);
        Thread.sleep(1000);
        currentTime = System.currentTimeMillis();
        redisService.zadd("zsort","access",currentTime,""+currentTime);
        Thread.sleep(1000);
        currentTime = System.currentTimeMillis();
        redisService.zadd("zsort","access",currentTime,""+currentTime);
        Thread.sleep(2000);
        currentTime = System.currentTimeMillis();
        redisService.zadd("zsort","access",currentTime,""+currentTime);
        Thread.sleep(3000);
        currentTime = System.currentTimeMillis();
        redisService.zadd("zsort","access",currentTime,""+currentTime);

        return "success";
    }

    @RequestMapping("testzsortGet")
    @ResponseBody
    public String testzsortGet(@RequestParam("seconds") Integer second) {
        long current = System.currentTimeMillis();
        long last = current - second * 1000;

        long x = redisService.zcount("zsort","access",last,current);
        System.out.println(x);
        return "success";
    }
}
