package com.imooc.netty;

import java.lang.reflect.Method;

/**
 * Created by hukai on 2018/8/26.
 */
public class BeanMethod {

    private Object bean;
    private Method method;


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
