package com.imooc.controller;

import com.imooc.dataobject.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hukai on 2018/8/5.
 */
@Controller
public class JvmTestController {


    private List<User> userlist = new ArrayList<User>();

    @GetMapping("heapout")
    @ResponseBody
    public String heap(){
        Integer i = 0;
        while (true) {
            userlist.add(new User(i++,"111","222","333"));
            if (i < 0){
                break;
            }
        }

        return "heap";
    }


}
