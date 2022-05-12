package com.fdm.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
public class ForexPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForexPlatformApplication.class, args);
	}

}
