package com.imooc.netty.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by hukai on 2018/11/6.
 */


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface RemoteInvoke {
}
