package com.sparta.impostor.commerce.backend.common.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @EntityScan, @EnableJpaRepositories 의 basePackages 는 @SpringBootApplication 의 scanBasePackages 의
 * 범위 내에 포함되도록 설정했기 때문에 명시하지 않아도 동작하지만,
 * 해당 패키지 내의 Repository와 Entity 클래스가 위치한 곳을 명확히 알려주기 위하여 추가하였습니다.
 */
@Configuration
@EnableJpaAuditing
@EntityScan(basePackages = "com.sparta.impostor.commerce.backend.domain")
@EnableJpaRepositories(basePackages = "com.sparta.impostor.commerce.backend.domain")
public class JPAConfiguration {

    @PersistenceContext
    private EntityManager em;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(em);
    }
}