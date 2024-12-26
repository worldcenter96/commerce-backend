package com.sparta.common.config;

import com.sparta.common.interceptor.LoggingInterceptor;
import com.sparta.common.logging.TraceIdFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CommonConfig implements WebMvcConfigurer {

    // TraceIdFilter 를 필터로 등록하는 메서드
    @Bean
    public FilterRegistrationBean<Filter> traceIdFilter() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TraceIdFilter());
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.setOrder(1); // 필터 순서 (낮은 순서가 먼저 실행)

        return registrationBean;
    }

    // 로깅 인터셉터 등록
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggingInterceptor()) // LoggingInterceptor 등록
                .addPathPatterns("/api/*");

    }
}
