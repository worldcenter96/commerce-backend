package com.sparta.b2c.product.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "com.sparta.impostor.commerce.backend")
@EnableJpaRepositories(basePackages = "com.sparta.impostor.commerce.backend")
public class JpaConfig {
}
