package com.sunny.pms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.sunny.pms.dao")
public class PmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PmsApplication.class, args);
	}

}
