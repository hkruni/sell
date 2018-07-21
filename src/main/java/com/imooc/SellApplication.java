package com.imooc;

import com.imooc.dataobject.User;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync //支持异步调用
public class SellApplication {

	public static void main(String[] args) {
		SpringApplication.run(SellApplication.class, args);

	}
}
