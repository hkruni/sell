package com.imooc.dto;

/**
 * Created by hukai on 2018/8/12.
 */
public class Message<T> {

    private String id;
    private Long time;
    private T t;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
