package com.minimarket.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MinimarketApplication {

	public static void main(String[] args) {
		
		System.setProperty("norm.jdbcUrl", "jdbc:mysql://localhost:3306/bdmovil?useSSL=false");
		System.setProperty("norm.user", "root");
		System.setProperty("norm.password", "123456");
		
		SpringApplication.run(MinimarketApplication.class, args);
	}

}
