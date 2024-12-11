package com.sparta.common.resolver;

import com.sparta.common.annotation.LoginMember;
import com.sparta.common.dto.MemberSession;
import com.sparta.common.utils.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpServletRequest request;
    private final SessionUtil sessionUtil;
    private final RedisTemplate<String, MemberSession> redisTemplate;
    private static final Map<String, String> SESSION_COOKIE_MAP = Map.of(
            "ADMIN", "ADMIN_SESSION",
            "B2B", "B2B_SESSION",
            "B2C", "B2C_SESSION"
    );

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMember.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        LoginMember loginMember = parameter.getParameterAnnotation(LoginMember.class);
        String role = loginMember.role().toString();
        String cookieName = SESSION_COOKIE_MAP.get(role);

        String sessionId = sessionUtil.getSessionIdFromCookies(request, cookieName);
        if (sessionId != null) {
            return redisTemplate.opsForValue().get(cookieName + ":" + sessionId);
        }

        return null;
    }

}
