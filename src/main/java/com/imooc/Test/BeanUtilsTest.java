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

        User u1 = new User();
        u1.setName("hukai");
        u1.setAge(12);
        u1.setBirthday(new Date());
        ContactInfo info = new ContactInfo();
        info.setAddress("北京市");
        info.setPhone("15117945727");
        info.setEmail("hkruni@163.com");
        u1.setContactInfo(info);


        User u2 = new User();
        BeanUtils.copyProperties(u1,u2);//复制user对象
        System.out.println(u2.getBirthday() == u1.getBirthday());///true
        System.out.println(u2.getName() == u1.getName());//true
        System.out.println(u1.getContactInfo() == u2.getContactInfo());//但是引用对象是浅复制 true
        BeanUtils.copyProperties(u1.getContactInfo(),u2.getContactInfo());
        System.out.println(u2);
        System.out.println(u2.getContactInfo() == u1.getContactInfo());//true

        System.out.println("-------------");
        BeanUser u3 = new BeanUser();
        BeanUtils.copyProperties(u1,u3);//ContactInfo的属性无法赋值到里面
        System.out.println(u3);
        BeanUtils.copyProperties(info,u3);//所以需要再次复制ContactInfo
        System.out.println(u3);






    }
}
