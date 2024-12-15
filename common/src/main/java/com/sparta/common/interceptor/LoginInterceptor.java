package com.sparta.common.interceptor;

import com.sparta.common.annotation.CheckAuth;
import com.sparta.common.annotation.CheckLogin;
import com.sparta.common.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final SessionService sessionService;
    private static final Map<String, String> SESSION_COOKIE_MAP = Map.of(
            "ADMIN", "ADMIN_SESSION",
            "B2B", "B2B_SESSION",
            "B2C", "B2C_SESSION"
    );

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod handlerMethod) {
            CheckLogin checkLogin = handlerMethod.getMethodAnnotation(CheckLogin.class);
            if (checkLogin != null) {
                String role = checkLogin.role().toString();
                String cookieName = SESSION_COOKIE_MAP.get(role);

                if (cookieName != null && sessionService.extendSession(request, cookieName)) {
                    return false;
                }
            }
        }

        return true;

    }
}
