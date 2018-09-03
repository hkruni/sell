package com.imooc.netty;


import com.imooc.object.User;

import java.util.Date;

/**
 * Created by hukai on 2018/8/22.
 */
public class TestTcp2 {

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
