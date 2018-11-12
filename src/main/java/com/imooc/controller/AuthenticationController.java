package com.imooc.controller;


import com.imooc.dataobject.User;
import com.imooc.enums.ResultEnum;
import com.imooc.service.UserService;
import com.imooc.util.ResultVOUtil;
import com.imooc.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthenticationController {

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public Object login(HttpServletResponse httpServletResponse,User user) {
        String  token = userService.login(httpServletResponse,user);
        return ResultVOUtil.success(token);
    }
}
