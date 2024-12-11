package com.sparta.common.interceptor;

import com.sparta.common.annotation.CheckAuth;
import com.sparta.common.dto.MemberSession;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final RedisTemplate<String, MemberSession> redisTemplate;
    private final static String SESSION_COOKIE_NAME = "SESSION";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod handlerMethod) {
            CheckAuth checkAuth = handlerMethod.getMethodAnnotation(CheckAuth.class);
            if (checkAuth != null) {
                String sessionId = getSessionIdFromCookies(request);

                if (sessionId == null || redisTemplate.opsForValue().get(sessionId) == null) {
                    throw new IllegalArgumentException("로그인이 필요합니다.");
                }
            }
        }

        return true;
    }

    private String getSessionIdFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies())
                    .filter(cookie -> SESSION_COOKIE_NAME.equals(cookie.getName()))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
}
