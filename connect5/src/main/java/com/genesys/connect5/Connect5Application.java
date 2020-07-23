package com.genesys.connect5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Connect5Application {

	public static void main(String[] args) {
		SpringApplication.run(Connect5Application.class, args);
	}

}
