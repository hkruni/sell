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

@RestController
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/login")
    public Object login(User user) {
        User userInDataBase = userService.getUserByUsercode(user.getUsercode());
        //JSONObject jsonObject = new JSONObject();
        if (userInDataBase == null) {//用户名不存在
            return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
        } else if (!user.getPassword().equals(userInDataBase.getPassword())) {//密码错误
            return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
        } else {//验证通过
            String token = TokenUtil.getToken(userInDataBase);
            redisTemplate.opsForHash().put("session",token,user);
            redisTemplate.multi();
            return ResultVOUtil.success(token);
        }
    }
}
