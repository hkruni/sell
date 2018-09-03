package com.imooc.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;


public class DateTimeUtil {


    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";



    //获得当前日期是第几季度
    private static int getQuarterByMonth(Date date) {
        DateTime dateTime = new DateTime(date);
        int month = dateTime.getMonthOfYear();
        return  month % 3 == 0 ? month / 3 : month / 3 + 1;
    }


    //日期转字符串，默认格式
    public static Date strToDate(String dateTimeStr,String formatStr){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(formatStr);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }

    //字符串转，自定义格式
    public static String dateToStr(Date date,String formatStr){
        if(date == null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formatStr);
    }

    //字符串转日期
    public static Date strToDate(String dateTimeStr){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(STANDARD_FORMAT);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }

    //日期转字符串
    public static String dateToStr(Date date){
        if(date == null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(STANDARD_FORMAT);
    }

    /**
     * 计算两个日期之间差的天数
     * @param start
     * @param end
     * @return
     */
    public static int daysBetween(Date start ,Date end) {
        DateTime s_start = new DateTime(start);
        DateTime s_end = new DateTime(end);
        return Days.daysBetween(s_start,s_end).getDays();
    }

    //获得当前日期所在月的第一天和最后一天
    public static Pair<Date,Date> getFirstLastCurrentMonth(Date date) {
        DateTime d = new DateTime(date);
        return ImmutablePair.of(d.dayOfMonth().withMinimumValue().toDate(),d.dayOfMonth().withMaximumValue().toDate());

    }



    public static void main(String[] args) {






        Date start = strToDate("2018-07-01","yyyy-MM-dd");
        Date end = strToDate("2018-07-31","yyyy-MM-dd");

        System.out.println((end.getTime() - start.getTime()) / 86400000);

        System.out.println(getQuarterByMonth(start));
        System.out.println(getQuarterByMonth(end));

        DateTime dateTime = new DateTime(start);
        System.out.println(dateTime.getYear());//当前时间的年
        System.out.println(dateTime.year().get());//同上
        System.out.println(dateTime.monthOfYear().get());//月份
        System.out.println(dateTime.dayOfMonth().get());//日
        System.out.println(dateTime.dayOfYear().get());//一年的第几天
        System.out.println(dateTime.plusDays(3).toDate());//当前日期再加三天

        System.out.println(dateTime.dayOfMonth().getMaximumValue());//本月最后一天
        System.out.println(dateTime.dayOfMonth().getMinimumValue());//本月第一天
        System.out.println(dateTime.dayOfWeek().withMinimumValue());//本周第一天
        System.out.println(dateTime.dayOfWeek().withMaximumValue());//本周最后一天
        System.out.println(dateTime.dayOfWeek().get());//本周周几

        Pair<Date,Date> pair = getFirstLastCurrentMonth(start);
        System.out.println(pair.getLeft());
        System.out.println(pair.getRight());



        System.out.println("-----------");

        System.out.println(daysBetween(start,end));
        System.out.println(DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss"));
        System.out.println(DateTimeUtil.strToDate("2010-01-01 11:11:11","yyyy-MM-dd HH:mm:ss"));

    }


}
