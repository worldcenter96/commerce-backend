package com.sparta.b2c;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class B2cApplication {

    public static void main(String[] args) {
        SpringApplication.run(B2cApplication.class, args);
    }

}
