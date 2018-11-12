package com.imooc.exception;

import com.imooc.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class SellExceptionHandler {


    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public Object handlerException(Exception e) {
        e.printStackTrace();

        if (e instanceof  SellException) {
            return ResultVOUtil.error(((SellException) e).getCode(),e.getMessage());
        } else {
            return ResultVOUtil.error(-1,"系统异常");
        }

    }
}
