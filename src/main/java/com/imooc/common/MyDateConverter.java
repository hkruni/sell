package com.imooc.common;

import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *把Source类型String转化为Target类型 Date
 */
@Component
public class MyDateConverter implements Converter<String,Date> {
    public Date convert(String source) {
        System.out.println("convert转化：" + source);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
