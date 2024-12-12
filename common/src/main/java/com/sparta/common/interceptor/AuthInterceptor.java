package com.sparta.common.interceptor;

import com.sparta.common.annotation.CheckAuth;
import com.sparta.common.utils.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final SessionUtil sessionUtil;
    private static final Map<String, String> SESSION_COOKIE_MAP = Map.of(
            "ADMIN", "ADMIN_SESSION",
            "B2B", "B2B_SESSION",
            "B2C", "B2C_SESSION"
    );


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod handlerMethod) {
            CheckAuth checkAuth = handlerMethod.getMethodAnnotation(CheckAuth.class);
            if (checkAuth != null) {
                String role = checkAuth.role().toString();
                String cookieName = SESSION_COOKIE_MAP.get(role);

                if (cookieName == null || !sessionUtil.isSessionValid(request, cookieName)) {
                    throw new IllegalArgumentException("로그인이 필요합니다.");
                }
            }
        }

        return true;
    }
}
