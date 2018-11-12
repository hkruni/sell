package com.imooc.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by hukai on 2018/7/21.
 */

//@Aspect
//@Component
//@Order(2)
public class Service2Aspect {

    @Pointcut("execution(public * com.imooc.service.*.*(..))")
    public void method3(){}




    @Around("method3()")
    public void around3(ProceedingJoinPoint joinPoint) {
        System.out.println("method3拦截前");
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException();
        }
        System.out.println("method3拦截后");
    }



}
