package com.sparta.common.logging;

import java.io.IOException;
import java.util.UUID;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

public class TraceIdFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        String traceId = UUID.randomUUID().toString(); // traceId 생성
        MDC.put("traceId", traceId); // MDC 에 traceId 설정

        try {
            // 요청을 다음 필터나 서블릿으로 전달
            chain.doFilter(request, response);
        } finally {
            // 요청 후 MDC 에서 traceId 제거
            MDC.clear();
        }

    }
}
