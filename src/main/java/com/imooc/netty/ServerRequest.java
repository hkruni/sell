package com.imooc.netty;

/**
 * Created by hukai on 2018/8/22.
 */
public class ServerRequest {

    private Long id;//客户端请求ID

    private Object content;//客户端内容

    private String command;//访问的类和方法

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Object getContent() {
        return content;
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
