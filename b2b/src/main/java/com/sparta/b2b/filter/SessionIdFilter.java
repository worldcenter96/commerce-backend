package com.sparta.b2b.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class SessionIdFilter extends OncePerRequestFilter {

//    private static final String SESSION_KEY = "JSESSIONID";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(shouldNotFilter(request)){
            filterChain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }


        filterChain.doFilter(request, response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        List<String> excludedPaths = List.of(
                "/api/b2b-members/signup",
                "/api/b2b-members/login"
        );

        return excludedPaths.stream()
                .anyMatch(path -> StringUtils.startsWithIgnoreCase(request.getRequestURI(), path));
    }
}
