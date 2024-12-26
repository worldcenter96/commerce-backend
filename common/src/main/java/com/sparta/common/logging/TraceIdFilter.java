package com.sparta.common.logging;

import java.io.IOException;
import java.util.UUID;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.slf4j.MDC;

public class TraceIdFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String traceId = UUID.randomUUID().toString(); // traceId 생성
        MDC.put("traceId", traceId); // MDC 에 traceId 설정

        try {
            // 요청을 다음 필터나 서블릿으로 전달
            chain.doFilter(request, response);
        } finally {
            // 요청 후 MDC 에서 traceId 제거
            MDC.remove("traceId");
        }
    }

}
