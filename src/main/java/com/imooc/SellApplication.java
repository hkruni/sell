package com.imooc;

import com.imooc.dataobject.User;
import com.imooc.filter.CrosFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SellApplication {

	public static void main(String[] args) {
		SpringApplication.run(SellApplication.class, args);

	}
	@Bean
	public FilterRegistrationBean registerFilter() {
		FilterRegistrationBean bean = new FilterRegistrationBean();
		bean.addUrlPatterns("/*");
		bean.setFilter(new CrosFilter());

		return bean;
	}
}
