package com.main.prevoyancehrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PrevoyancehrmApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrevoyancehrmApplication.class, args);
	}

}

