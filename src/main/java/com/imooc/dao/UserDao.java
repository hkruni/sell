package com.imooc.dao;

import com.imooc.dataobject.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    public User getUserByUsercode(@Param("usercode") String usercode);

    public void saveUser(User user);
}
