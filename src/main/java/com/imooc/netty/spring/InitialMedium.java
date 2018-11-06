package com.imooc.netty.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by hukai on 2018/8/26.
 * 中介者模式
 */
@Component
public class InitialMedium implements BeanPostProcessor{


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        return bean;
    }

    /**
     * spring容器启动时,所有controller方法都已放入beanMap
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        //由于controller被cglib代理了,所以无法取到注解,所以用注解获取到controller类不起作用
//        if (bean.getClass().isAnnotationPresent(Controller.class)) {

        //通过beanname获取controller
        if (beanName.endsWith("Controller")) {
            Method []methods = bean.getClass().getDeclaredMethods();
            for (Method m : methods) {
                Map<String ,BeanMethod> beanMap = Media.beanMap;
                //key为类名.方法名
                String  key = beanName + "." + m.getName();
                //value是beanmethod对象
                BeanMethod beanMethod = new BeanMethod();
                beanMethod.setBean(bean);
                beanMethod.setMethod(m);

                beanMap.put(key,beanMethod);

            }
        }
        return bean;
    }
}
