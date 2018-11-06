package com.imooc.intercept;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.imooc.aop.LoginRequired;
import com.imooc.dataobject.User;
import com.imooc.enums.ResultEnum;
import com.imooc.service.UserService;
import com.imooc.util.ResultVOUtil;
import com.imooc.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

/**
 * 登录校验拦截器
 * 对@LoginRequired注解的方法进行校验
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        // 判断接口是否需要登录
        LoginRequired methodAnnotation = method.getAnnotation(LoginRequired.class);
        if (methodAnnotation != null) {
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = null;

            String token = request.getParameter("token");
            if (token == null) {//没有登录
                out = response.getWriter();
                out.print(JSONObject.toJSONString(ResultVOUtil.error(ResultEnum.LOGIN_FAIL)));
                return false;
            }
            //String usercode = TokenUtil.getUseCode(token);
            User user = (User)redisTemplate.opsForHash().get("session",token);
            if (user != null && TokenUtil.isValidToken(token,user.getPassword())) {//token验证成功
                return true;
            }
            else{//token验证失败
                out = response.getWriter();
                out.print(JSONObject.toJSONString(ResultVOUtil.error(ResultEnum.LOGIN_FAIL)));
                return false;
            }


        }
        return true;
    }

    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }
}
