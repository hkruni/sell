package com.imooc.asyncListener;

import com.alibaba.druid.util.StringUtils;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;


public class MyAsyncListener implements AsyncListener {

    @Override
    public void onComplete(AsyncEvent asyncEvent) throws IOException {
        System.out.println("Listener线程 " + java.lang.Thread.currentThread().getName());

//        try {
//            System.out.println("Listener线程 " + java.lang.Thread.currentThread().getName());
//            AsyncContext asyncContext = asyncEvent.getAsyncContext();
//            ServletResponse response = asyncContext.getResponse();
//            ServletRequest request = asyncContext.getRequest();
//            response.setCharacterEncoding("utf-8");
//            PrintWriter out= response.getWriter();
//            if (request.getAttribute("timeout") != null &&
//                    StringUtils.equals("true",request.getAttribute("timeout").toString())) {//超时
//                out.println("后台线程执行超时---【回调】");
//                System.out.println("异步servlet【onComplete超时】");
//            }else {//未超时
//                out.println("后台线程执行完成---【回调】");
//                System.out.println("异步servlet【onComplete完成】");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    //异步线程出错时调用
    @Override
    public void onError(AsyncEvent asyncEvent) throws IOException {
        System.out.println("异步servlet错误");
    }

    //异步线程开始时调用
    @Override
    public void onStartAsync(AsyncEvent arg0) throws IOException {
        System.out.println("开始异步servlet");
    }


    //异步线程超时时调用
    @Override
    public void onTimeout(AsyncEvent asyncEvent) throws IOException {
        ServletRequest request = asyncEvent.getAsyncContext().getRequest();
        request.setAttribute("timeout", "true");
        System.out.println("异步servlet【onTimeout超时】");
    }

}