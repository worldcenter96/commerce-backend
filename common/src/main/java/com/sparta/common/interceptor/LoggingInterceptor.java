package com.sparta.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // MDC 에서 traceId 가져오기
        String traceId = MDC.get("traceId");
        String requestURI = request.getRequestURI();

        // 요청 시작 시간 기록 (후속 응답 시간 측정을 위해)
        request.setAttribute("startTime", System.currentTimeMillis());

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 요청 처리 후 로그 (응답시간)
        Long startTime = (Long) request.getAttribute("startTime");
        Long endTime = System.currentTimeMillis();
        Long duration = (endTime - startTime);

        String traceId = MDC.get("traceId");
        String requestURI = request.getRequestURI();

    }
}
