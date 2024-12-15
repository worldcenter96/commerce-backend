package com.sparta.common.service;

import com.sparta.common.dto.MemberSession;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.util.StandardSessionIdGenerator;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final RedisTemplate<String, MemberSession> redisTemplate;
    private final RedisTemplate<String, String> redisListTemplate;

    public String generateSession(String sessionName, Long memberId) {

        String newSessionId = new StandardSessionIdGenerator().generateSessionId();
        String sessionKey = sessionName + ":" + memberId.toString();

        redisListTemplate.opsForList().rightPush(sessionKey, newSessionId);
        redisTemplate.opsForValue().set(newSessionId, new MemberSession(memberId), 30L, TimeUnit.MINUTES);

        return newSessionId;

    }

    public boolean extendSession(HttpServletRequest request, String cookieName) {
        String sessionId = getSessionIdFromCookies(request, cookieName);
        return sessionId != null && redisTemplate.opsForValue().getAndExpire(sessionId, Duration.ofSeconds(1800)) != null;
    }

    public boolean isSessionValid(HttpServletRequest request, String cookieName) {
        String sessionId = getSessionIdFromCookies(request, cookieName);
        return sessionId != null && redisTemplate.opsForValue().get(sessionId) != null;
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

    public void deleteSession(String sessionName, Long memberId) {
        String sessionKey = sessionName + ":" + memberId.toString();
        List<String> sessionList = redisListTemplate.opsForList().range(sessionKey, 0, -1);

        if (sessionList != null) {
            sessionList.stream()
                    .filter(session -> redisTemplate.opsForValue().get(session) != null)
                    .forEach(redisTemplate::delete);
            redisListTemplate.delete(sessionKey);
        }
    }
}
