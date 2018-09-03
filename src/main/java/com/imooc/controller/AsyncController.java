package com.imooc.controller;


import com.imooc.asyncListener.MyAsyncListener;
import com.imooc.dataobject.ProductCategory;
import com.imooc.service.CategoryService;
import com.imooc.util.JsonUtil;
import com.imooc.util.ResultVOUtil;
import com.imooc.vo.Query;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.tomcat.jni.Thread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.AsyncContext;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.Executor;

@Controller
public class AsyncController {

    @Autowired
    private ThreadPoolTaskExecutor executor;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "async" , method = RequestMethod.GET)
    @ResponseBody
    public void async(HttpServletRequest request) {

        System.out.println("主线程 " + java.lang.Thread.currentThread().getName());

        AsyncContext asyncContext = request.startAsync();
        asyncContext.setTimeout(5000);
        asyncContext.addListener(new MyAsyncListener());
        executor.submit(() -> {
            System.out.println("自定义线程 " + java.lang.Thread.currentThread().getName());
            List<ProductCategory> list = categoryService.findAll(new Query());

            ServletResponse response = asyncContext.getResponse();
            response.setCharacterEncoding("utf-8");
            PrintWriter out = null;
            try {
                out= response.getWriter();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String result = JsonUtil.obj2String(list);
            out.print(result);
            asyncContext.complete();
        });
        System.out.println("主线程完毕");


    }
}
