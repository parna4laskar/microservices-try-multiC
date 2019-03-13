package com.ms.mstry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
//@EnableEurekaClient
public class MsTryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsTryApplication.class, args);
	}
}
