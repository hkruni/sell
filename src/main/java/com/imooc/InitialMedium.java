package com.imooc;

import com.imooc.netty.BeanMethod;
import com.imooc.netty.Media;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

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

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        //System.out.println("postProcessAfterInitialization : " + beanName);
        //由于 controller被cglib代理了,所以无法取到注解
//        if (bean.getClass().isAnnotationPresent(Controller.class)) {
        if (beanName.endsWith("Controller")) {
            //System.out.println(bean.getClass().getName());
            Method []methods = bean.getClass().getDeclaredMethods();
            for (Method m : methods) {
                String  key = beanName + "." + m.getName();
                Map<String ,BeanMethod> beanMap = Media.beanMap;
                BeanMethod beanMethod = new BeanMethod();
                beanMethod.setBean(bean);
                beanMethod.setMethod(m);
                beanMap.put(key,beanMethod);

            }
        }
        return bean;
    }
}
