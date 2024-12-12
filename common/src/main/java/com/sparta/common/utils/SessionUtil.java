package com.sparta.common.utils;

import com.sparta.common.dto.MemberSession;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class SessionUtil {

    private final RedisTemplate<String, MemberSession> redisTemplate;

    public boolean isSessionValid(HttpServletRequest request, String cookieName) {
        String sessionId = getSessionIdFromCookies(request, cookieName);
        return sessionId != null && redisTemplate.opsForValue().get(cookieName + ":" + sessionId) != null;

    }

    public String getSessionIdFromCookies(HttpServletRequest request, String cookieName) {
        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies())
                    .filter(cookie -> cookieName.equals(cookie.getName()))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
}
