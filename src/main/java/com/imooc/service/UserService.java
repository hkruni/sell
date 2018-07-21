package com.imooc.service;

import com.imooc.dao.UserDao;
import com.imooc.dataobject.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User getUserByUsercode(String usercode) {
        return userDao.getUserByUsercode(usercode);
    }



}
