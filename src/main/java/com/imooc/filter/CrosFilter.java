package com.imooc.filter;


import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by hukai on 2018/10/20.
 */
public class CrosFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) servletResponse;




        HttpServletRequest req = (HttpServletRequest)servletRequest;
        String origin = req.getHeader("Origin");
        if (StringUtils.isNotEmpty(origin)) {
            res.addHeader("Access-Control-Allow-Origin",origin);
        }

        //支持所有自定义头
        String headers = req.getHeader("Access-Control-Request-Headers");
        if (StringUtils.isNotEmpty(headers)) {
            System.out.println(headers);
            res.addHeader("Access-Control-Allow-Headers",headers);
        }

        res.addHeader("Access-Control-Allow-Methods","GET");
        res.addHeader("Access-Control-Allow-Headers","content-type");

        //cookies时用
        res.addHeader("Access-Control-Allow-Credentials","true");

        //res.addHeader("Access-Control-Allow-Origin","*");
        //res.addHeader("Access-Control-Allow-Methods","*");
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
