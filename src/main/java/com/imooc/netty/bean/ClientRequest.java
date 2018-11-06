package com.imooc.netty.bean;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 客户端发送请求的封装
 */
public class ClientRequest {

    private final long id;//每个请求的唯一ID识别

    private Object content;//发送内容

    private final AtomicLong aid = new AtomicLong(1);

    private String command;//类和方法名称,spring中用

    public ClientRequest() {
        id = aid.incrementAndGet();
    }

    public Object getContent() {
        return content;
    }

    public AtomicLong getAid() {
        return aid;
    }

    public long getId() {
        return id;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
