package com.sparta.b2b;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.sparta"})
public class B2bApplication {

	public static void main(String[] args) {
		SpringApplication.run(B2bApplication.class, args);

	}

}
