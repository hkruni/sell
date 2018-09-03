package com.imooc.netty;

import com.alibaba.fastjson.JSONObject;
import com.imooc.controller.ESXunwuController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by hukai on 2018/8/26.
 */
public class Media {

    public static Map<String ,BeanMethod> beanMap;
    static {
        beanMap = new HashMap<String, BeanMethod>();
    }
    private static  Media m = null;
    private Media(){

    }

    public static Media newInstance() {
        if (m == null) {
            m = new Media();
        }
        return m;
    }

    public Response process(ServerRequest request) {

        Response result = null;

        String command = request.getCommand();
        BeanMethod beanMethod = beanMap.get(command);
        if (beanMethod == null) {
            return null;
        }
        Object bean = beanMethod.getBean();
        Method m = beanMethod.getMethod();
        Class paramType = m.getParameterTypes()[0];
        Object content = request.getContent();

        Object args = JSONObject.parseObject(JSONObject.toJSONString(content),paramType);

        try {
            result = (Response) m.invoke(bean,args);
            result.setId(request.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

}
