package com.imooc.netty;

/**
 * Created by hukai on 2018/8/22.
 */
public class TestTcp {

    public static void main(String[] args) {
        ClientRequest request = new ClientRequest();
        request.setContent("这是TCP长连接请求");
        Response response = TcpClient.send(request);
        System.out.println(response.getResult());
    }
}
