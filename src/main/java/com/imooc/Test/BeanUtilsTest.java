package com.imooc.Test;

import com.imooc.object.BeanUser;
import com.imooc.object.ContactInfo;
import com.imooc.object.User;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * Created by hukai on 2018/8/28.
 */
public class BeanUtilsTest {

    public static void main(String[] args) {

        User user = new User();
        user.setName("hukai");
        user.setAge(12);
        user.setBirthday(new Date());
        ContactInfo info = new ContactInfo();
        info.setAddress("北京市");
        info.setPhone("15117945727");
        info.setEmail("hkruni@163.com");
        user.setContactInfo(info);


        User u = new User();
        BeanUtils.copyProperties(user,u);//复制user对象
        System.out.println(u.getBirthday() == user.getBirthday());
        System.out.println(u.getName() == user.getName());
        //System.out.println(u);
        //System.out.println(user.getContactInfo() == u.getContactInfo());//但是引用对象是浅复制
        BeanUtils.copyProperties(user.getContactInfo(),u.getContactInfo());
        System.out.println(u);
        System.out.println(u.getContactInfo() == user.getContactInfo());

        System.out.println("-------------");
        BeanUser beanUser = new BeanUser();
        BeanUtils.copyProperties(user,beanUser);//ContactInfo的属性无法赋值到里面
        System.out.println(beanUser);
        BeanUtils.copyProperties(info,beanUser);//所以需要再次复制ContactInfo
        System.out.println(beanUser);






    }
}
