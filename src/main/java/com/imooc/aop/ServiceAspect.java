package com.imooc.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by hukai on 2018/7/21.
 */

@Aspect
@Component
public class ServiceAspect {

    @Pointcut("execution(public * com.imooc.service.*.*())")
    public void method1(){}


    @Pointcut("execution(public * com.imooc.service.*.*())")
    public void method2(){}

    @Around("method1()")
    public void around1(ProceedingJoinPoint joinPoint) {
        System.out.println("method1拦截前");
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("method1拦截后");
    }

    @Around("method2()")
    public void around2(ProceedingJoinPoint joinPoint) {
        System.out.println("method2拦截前");
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("method2拦截后");
    }

}
