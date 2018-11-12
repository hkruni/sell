package com.imooc.service;

import com.imooc.dao.GoodDao;
import com.imooc.dao.UserDao;
import com.imooc.dataobject.Good;
import com.imooc.dataobject.User;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private GoodDao goodDao;

    @Autowired
    private RedisService redisService;

    public static final String COOKI_NAME_TOKEN = "token";

    private static final int maxAge = 10 * 24 * 60 * 60;


    public User getUserByUsercode(String usercode) {
        return userDao.getUserByUsercode(usercode);
    }

    public String login(HttpServletResponse response,User user) {

        User realUser = userDao.getUserByUsercode(user.getUsercode());
        if (realUser == null) {
            throw new SellException(ResultEnum.LOGIN_FAIL);
        }

        if (!realUser.getPassword().equals(user.getPassword())) {
            throw new SellException(ResultEnum.LOGIN_FAIL);
        }

        String token = TokenUtil.generateToken();
        addCookie(response,token,realUser);

        return token;

    }

    private void addCookie(HttpServletResponse response, String token, User user) {
        redisService.set("token",token,user);
        Cookie cookie = new Cookie(COOKI_NAME_TOKEN, token);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @Transactional
    public void insertUserGood() {
        User user = new User();
        user.setUsername("xiaoming");
        user.setPassword("123456");
        user.setUsercode("21213");

        Good good = new Good();
        good.setName("good1");
        good.setType(20);

        userDao.saveUser(user);
        int x = 1 / 0;
        goodDao.saveGood(good);

    }

    @Transactional(isolation = Isolation.READ_COMMITTED,timeout = 100)
    public void readMultiTimes() {
        User u = userDao.getUserByUsercode("111");
        System.out.println(u);

        u = userDao.getUserByUsercode("111");
        System.out.println(u);



    }


}
