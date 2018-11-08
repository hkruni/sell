package com.imooc.controller.nettyremote;

import com.imooc.netty.annotation.Remote;
import com.imooc.netty.bean.Response;
import com.imooc.netty.spring.ResponseUtil;
import com.imooc.object.User;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by hukai on 2018/11/6.
 */

@Remote
public class UserRemote {

    public Response saveUser(User user) {
        System.out.println(user);

        return ResponseUtil.createSuccessResult(user);
    }

    public Response saveUsers(List<User> users) {


        return ResponseUtil.createSuccessResult(users);
    }
}
