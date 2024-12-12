package com.sparta.common.utils;

import com.sparta.common.dto.MemberSession;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.util.StandardSessionIdGenerator;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class SessionUtil {

    private final RedisTemplate<String, MemberSession> redisTemplate;
    private final RedisTemplate<String, Object> redisListTemplate;

    public String generateSession(String sessionName, Long memberId) {
        String sessionId = new StandardSessionIdGenerator().generateSessionId();
        String sessionKey = sessionName + ":" + memberId.toString();

        redisListTemplate.opsForList().rightPush(sessionKey, sessionId);
        redisTemplate.opsForValue().set(sessionId, new MemberSession(memberId), 30L, TimeUnit.MINUTES);

        return sessionId;
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
        List<Object> sessionList = redisListTemplate.opsForList().range(sessionKey, 0, -1);

        if (sessionList != null) {
            sessionList.stream()
                    .filter(session -> redisTemplate.opsForValue().get(session) != null)
                    .forEach(session -> redisTemplate.delete((String) session));
            redisListTemplate.delete(sessionKey);
        }
    }
}
