package com.sparta.common.aspect;

import com.sparta.common.annotation.RedissonLock;
import com.sparta.common.utils.CustomSpringELParser;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@RequiredArgsConstructor
public class RedissonLockAspect {

    private final RedissonClient redissonClient;
    private static final Logger log = LoggerFactory.getLogger(RedissonLockAspect.class);

    @Around("@annotation(com.sparta.common.annotation.RedissonLock)")
    public void redissonLock(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RedissonLock annotation = method.getAnnotation(RedissonLock.class);
        String lockKey = method.getName() + CustomSpringELParser.getDynamicValue(signature.getParameterNames(), joinPoint.getArgs(), annotation.value());

        RLock lock = redissonClient.getLock(lockKey);
        try {
            boolean lockable = lock.tryLock(annotation.waitTime(), annotation.leaseTime(), TimeUnit.MILLISECONDS);
            if (!lockable) {
                log.info("Lock 획득 실패={}", lockKey);
                return;
            }
            log.info("로직 수행");
            joinPoint.proceed();
        } catch (InterruptedException ex) {
            log.info("에러 발생");
            throw ex;
        } finally {
            log.info("락 해제 시도");
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
                log.info("락 해제 완료");
            } else {
                log.warn("현재 스레드가 락을 소유하고 있지 않아 해제하지 않음");
            }
        }


    }
}
