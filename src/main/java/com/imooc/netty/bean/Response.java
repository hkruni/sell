package com.imooc.netty.bean;

/**
 * Created by hukai on 2018/8/22.
 */
public class Response {

    private Long id;//响应结果id

    private Object result;//响应结果

    private String code = "00000";//00000表示成功,其他表示失败

    private String msg;//失败原因

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
