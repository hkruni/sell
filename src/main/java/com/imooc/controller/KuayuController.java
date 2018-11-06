package com.imooc.controller;

import com.imooc.dataobject.User;
import com.imooc.util.ResultVOUtil;
import com.imooc.vo.ResultVO;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hukai on 2018/10/20.
 */

@RestController
@RequestMapping("/cors")
public class KuayuController {

    @GetMapping("/get1")
    public Object get1() {
        System.out.println("KuayuController.get1()");
        return ResultVOUtil.success("data get1");
    }


    @PostMapping("/postjson")
    public Object postjson(@RequestBody User user) {
        System.out.println("KuayuController.postjson()");
        return user;

    }

    @GetMapping("/getCookie")
    public Object getCookie(@CookieValue(value="cookie1") String cookie1) {
        System.out.println("KuayuController.getCookie()");
        return ResultVOUtil.success("getCookie :" + cookie1);
    }

    @GetMapping("/getHeader")
    public Object getHeader(@RequestHeader("x-header1")String header1,
                            @RequestHeader("x-header2")String header2) {
        System.out.println("KuayuController.getHeader()");
        return ResultVOUtil.success("getHeader :" + header1 + "  " + header2);
    }



}
