package com.imooc.netty.spring;

import java.lang.reflect.Method;

/**
 *bean方法的封装
 */
public class BeanMethod {

    private Object bean;//所属bean
    private Method method;//方法


    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }
}
