package com.imooc.netty.spring;

import com.alibaba.fastjson.JSONObject;
import com.imooc.netty.bean.Response;
import com.imooc.netty.bean.ServerRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 *Media作为一个中介
 *对所有controller的方法进行代理
 */
public class Media {


    //key是Controller类名.方法名   value是类method的封装
    public static Map<String ,BeanMethod> beanMap;

    static {
        beanMap = new HashMap<String, BeanMethod>();
    }

    private static Media m = null;

    private Media(){

    }

    //单例模式
    public synchronized static Media  newInstance() {
        if (m == null) {
            m = new Media();
        }
        return m;
    }

    /**
     * 接受客户端的请求获取需要访问的controller方法然后代理访问
     * @param request
     * @return
     */
    public Response process(ServerRequest request) {

        Response result = null;

        String command = request.getCommand();
        BeanMethod beanMethod = beanMap.get(command);
        if (beanMethod == null) {//接口方法不存在
            return null;
        }

        Object bean = beanMethod.getBean();
        Method m = beanMethod.getMethod();
        Class paramType = m.getParameterTypes()[0];
        Object content = request.getContent();

        Object args = JSONObject.parseObject(JSONObject.toJSONString(content),paramType);//方法的参数

        try {
            result = (Response) m.invoke(bean,args);
            result.setId(request.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

}
