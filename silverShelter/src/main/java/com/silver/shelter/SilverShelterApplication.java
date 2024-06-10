package com.silver.shelter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class SilverShelterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SilverShelterApplication.class, args);
	}

}
