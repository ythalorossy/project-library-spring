package com.library.categoriesservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CategoriesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CategoriesServiceApplication.class, args);
	}

}
