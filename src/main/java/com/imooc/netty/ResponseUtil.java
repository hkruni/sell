package com.imooc.netty;

/**
 * Created by hukai on 2018/8/26.
 */
public class ResponseUtil {

    public static Response createSuccessResult() {
        return new Response();

    }

    public static Response createFailResult(String code,String msg) {
        Response result = new Response();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static Response createSuccessResult(Object content) {
        Response response = new Response();
        response.setResult(content);
        return response;
    }
}
