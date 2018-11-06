package com.imooc.netty;

import com.imooc.netty.bean.ClientRequest;
import com.imooc.netty.bean.Response;
import com.imooc.netty.client.TcpClient;

/**
 * Created by hukai on 2018/8/22.
 */
public class TestTcp {

    public static void main(String[] args) {
        //构建客户端请求
        ClientRequest request = new ClientRequest();
        request.setContent("这是TCP长连接请求");

        //发送请求
        Response response = TcpClient.send(request);


        System.out.println(response.getResult());
    }
}
