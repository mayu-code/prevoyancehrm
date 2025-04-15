package com.main.prevoyancehrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@ComponentScan(basePackages = "com.main.prevoyancehrm")
@SpringBootApplication
@EnableAsync
public class PrevoyancehrmApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrevoyancehrmApplication.class, args);
	}

}

