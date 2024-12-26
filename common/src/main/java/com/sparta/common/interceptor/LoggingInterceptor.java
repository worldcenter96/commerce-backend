package com.sparta.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class LoggingInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // MDC 에서 traceId 가져오기
        String traceId = MDC.get("traceId");
        String requestURI = request.getRequestURI();

        // 요청 시작 로그
        if (traceId != null) {
            System.out.println("[TRACE_ID: " + traceId + "] Incoming request: " + requestURI);
        }

        // 요청 시작 시간 기록 (후속 응답 시간 측정을 위해)
        request.setAttribute("traceId", System.currentTimeMillis());

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

        // 응답 시간 로그
        if (traceId != null) {
            System.out.println("[TRACE_ID: " + traceId + "] Completed request: " + requestURI + " in " + duration + " ms");
        }
    }
}
