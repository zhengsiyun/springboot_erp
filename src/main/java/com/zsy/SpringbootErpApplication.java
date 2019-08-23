package com.zsy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages= {"com.zsy.sys.mapper","com.zsy.bus.mapper"})
public class SpringbootErpApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootErpApplication.class, args);
	}
	
}
