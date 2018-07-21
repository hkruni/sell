package com.imooc.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.imooc.dataobject.User;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.UUID;

public class TokenUtil {

    public static  String getToken(User user) {
        String token = "";
        try {
            token = JWT.create()
                    .withJWTId(UUID.randomUUID().toString())
                    .withIssuer("chinamobile.com")
                    .withExpiresAt(new Date(System.currentTimeMillis() + 600000))
                    .withAudience(user.getUsercode(),user.getUsername())
                    .sign(Algorithm.HMAC256(user.getPassword()));   // 以 password 作为 token 的密钥
        } catch (UnsupportedEncodingException ignore) {
        }
        return token;
    }


    public static boolean isValidToken(String token,String sign) {
        JWTVerifier verifier = null;
        try {
            verifier = JWT.require(Algorithm.HMAC256(sign)).build();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        verifier.verify(token);
        return true;
    }


    public static String getUseCode(String token) {
        String res = null;
        try{
            res = JWT.decode(token).getAudience().get(0);
        }catch (JWTDecodeException e) {

        }
        return res;

    }

    public static void main(String[] args) {

        User user = new User();
        user.setId(1);
        user.setUsercode("hukairuni");
        user.setUsername("胡凯");
        user.setPassword("19980101@qq");

        String token = getToken(user);

        System.out.println(token);

        try {
            JWTVerifier verifier =  JWT.require(Algorithm.HMAC256(user.getPassword())).build();
            try {
                verifier.verify("eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOlsiaHVrYWlydW5pIiwi6IOh5YevIl0sImlzcyI6ImNoaW5hbW9iaWxlLmNvbSIsImV4cCI6MTUyMDIwNjI2NCwianRpIjoiNmQ5NThlN2UtOWE3NC00YjU1LTk2OTktNWU4ZDVjNDFkMmZkIn0.gmtbqev8fIFtYzQSfGrhaBG8UdC0zoDMJhVSRUt-9H0");
            } catch (JWTVerificationException e) {
                System.out.println("验证失败");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.out.println("验证成功");

//        System.out.println(JWT.decode(token).getAudience().get(0));//Audience数组第0个元素
//        System.out.println(JWT.decode(token).getAudience().get(1));
//        System.out.println(JWT.decode(token).getIssuer());//获取withIssuer设置的值
//        System.out.println(JWT.decode(token).getId());//获取JWTId
//        System.out.println(JWT.decode(token).getExpiresAt());//获取过期时间

    }
}
