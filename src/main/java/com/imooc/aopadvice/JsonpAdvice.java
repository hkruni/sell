package com.imooc.aopadvice;

import jdk.nashorn.api.scripting.AbstractJSObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;


@ControllerAdvice
public class JsonpAdvice  extends AbstractJsonpResponseBodyAdvice{

    public JsonpAdvice() {
        super("callback");
    }
}
