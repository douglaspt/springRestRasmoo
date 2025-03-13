package com.client.ws.dptplus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableCaching
public class WsDptPlusApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsDptPlusApplication.class, args);
	}

}
