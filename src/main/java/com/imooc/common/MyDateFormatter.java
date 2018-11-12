package com.imooc.common;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * 自定义formatter，对Date类型参数进行转化和解析
 */
//@Component
public class MyDateFormatter implements Formatter<Date> {

    public Date parse(String text, Locale locale) throws ParseException {
        System.out.println("formatter 转化:" + text);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(text);
    }

    public String print(Date object, Locale locale) {
        return null;
    }
}
