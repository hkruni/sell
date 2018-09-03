package com.imooc.controller;

import com.imooc.netty.Response;
import com.imooc.netty.ResponseUtil;
import com.imooc.object.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by hukai on 2018/8/26.
 */

@Controller
public class NettyController {


    @RequestMapping("user")
    public Response saveUser(User user) {
        System.out.println(user);

        return ResponseUtil.createSuccessResult(user);
    }

    @RequestMapping("users")
    public Response saveUsers(List<User> users) {


        return ResponseUtil.createSuccessResult(users);
    }
}
