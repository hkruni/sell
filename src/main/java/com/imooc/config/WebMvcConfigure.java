package com.imooc.config;

import com.imooc.common.MyDateConverter;
import com.imooc.common.MyDateFormatter;
import com.imooc.intercept.AuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class WebMvcConfigure extends WebMvcConfigurerAdapter {



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**");
        super.addInterceptors(registry);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        //registry.addFormatter(formatter());
        registry.addConverter(converter());
        super.addFormatters(registry);
    }

//    public MyDateFormatter formatter() {
//        return new MyDateFormatter();
//    }
//

    public MyDateConverter converter() {
        return new MyDateConverter();
    }



    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }

}