package com.imooc.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class HttpAspect {

    private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class);

    /**
     * 定义AOP扫描路径
     */
    @Pointcut("execution(public * com.imooc.controller.*.*())")
    public void log(){}

    /**
     * 记录HTTP请求开始时的日志
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String url = request.getRequestURI();
        String method = request.getMethod();
        String ip = request.getRemoteAddr();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String params = request.getQueryString();
        //Object []args = joinPoint.getArgs();

        logger.info("url:[" + url + "]\tmethod:[" + method + "]\tip:[" + ip + "]\tclassName:[" +className+ "]\tmethodName:[\" +methodName+ \"]\tparams:[" + params + "]");
    }

    /**
     * 记录HTTP请求结束时的日志
     */
    @After("log()")
    public void doAfter(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        logger.info("url = {} end of execution", request.getRequestURL());
    }

    /**
     * 获取返回内容
     */
    @AfterReturning(returning = "object",pointcut = "log()")
    public void doAfterReturn(Object object){
        logger.info("response={}",object.toString());
    }
}