package com.imooc.config;

import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class PageHelperConfiguration {


    private static final Logger log = LoggerFactory.getLogger(PageHelperConfiguration.class);
    @Bean
    public PageHelper pageHelper() {
        log.info("------Register MyBatis PageHelper");
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("dialect", "mysql");
        p.setProperty("offsetAsPageNum", "false");
        p.setProperty("rowBoundsWithCount", "false");
        p.setProperty("pageSizeZero", "true");
        p.setProperty("reasonable", "false");
        p.setProperty("supportMethodsArguments", "false");
        p.setProperty("returnPageInfo", "none");
        //通过设置pageSize=0或者RowBounds.limit = 0就会查询出全部的结果。
        pageHelper.setProperties(p);
        return pageHelper;
    }

}

