package com.imooc.netty;


import com.imooc.netty.bean.ClientRequest;
import com.imooc.netty.bean.Response;
import com.imooc.netty.client.TcpClient;
import com.imooc.object.User;

import java.util.Date;

/**
 * spring客户端访问
 */
public class TestSpringClient {

    public static void main(String[] args) {
        ClientRequest request = new ClientRequest();
        User u =  new User();
        u.setName("deshuang");
        u.setAge(20);
        u.setBirthday(new Date());

        request.setCommand("nettyController.saveUser");
        request.setContent(u);

        Response response = TcpClient.send(request);
        System.out.println(response.getResult());
    }
}
