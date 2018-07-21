package com.imooc.exception;

import com.imooc.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 廖师兄
 * 2017-07-30 17:44
 */
//@ControllerAdvice
public class SellExceptionHandler {


    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public Object handlerException() {
        return ResultVOUtil.error(-1,"系统异常");
    }
}
