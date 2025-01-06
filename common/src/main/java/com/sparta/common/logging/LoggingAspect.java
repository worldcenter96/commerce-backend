package com.sparta.common.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // 포인트컷 정의: com.sparta.controller 패키지 내의 모든 메서드에 적용
    @Pointcut("execution(* com.sparta..controller..*(..))")
    public void controllerMethods() {}

    // @Around 어노테이션을 사용하여 메서드 실행 전후에 로깅을 추가
    @Around("controllerMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        // 메서드 실행 전: traceId 가져오기, 없으면 새로 생성
        String traceId = MDC.get("traceId");
        if (traceId == null) {
            traceId = UUID.randomUUID().toString(); // traceId가 없으면 새로 생성
            MDC.put("traceId", traceId); // MDC 에 traceId 설정
        }

        // 메서드명 및 인자 추출
        String methodName = joinPoint.getSignature().getName();
        String methodArgs = Arrays.toString(joinPoint.getArgs());

        // 메서드 실행 전: 시간 기록
        long startTime = System.currentTimeMillis();

        // 메서드 실행
        Object result = joinPoint.proceed();

        // 메서드 실행 후: 시간 계산
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        // 로그를 찍고 응답 시간 기록
        logger.info("[TRACE_ID: {}] Executing method: {} with args: {} took {} ms", traceId, methodName, methodArgs, duration);

        return result;
    }
}
