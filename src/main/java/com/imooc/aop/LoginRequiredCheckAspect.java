package com.imooc.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Component
public class LoginRequiredCheckAspect {


    @Pointcut("execution(public * com.imooc.controller.*.*())")
    public void login(){}

//    @Before("login()")
//    public void loginCheck(JoinPoint joinPoint){
//        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
//        Method method = methodSignature.getMethod();
//        LoginRequired methodAnnotation = method.getAnnotation(LoginRequired.class);
//
//        if(methodAnnotation != null) {
//            System.out.println("该方法需要登录");
//        }
//    }

    @Around("login()")
    public Object loginCheckAround(ProceedingJoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        LoginRequired methodAnnotation = method.getAnnotation(LoginRequired.class);

        Object result = null;
        if(methodAnnotation != null) {
            System.out.println("该方法需要登录 :" + method.getName());
            try {
                result =  joinPoint.proceed(joinPoint.getArgs());
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

        return result;
    }

}
