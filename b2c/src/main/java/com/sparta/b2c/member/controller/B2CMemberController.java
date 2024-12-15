package com.sparta.b2c.member.controller;

import com.sparta.b2c.member.dto.request.LoginRequest;
import com.sparta.b2c.member.dto.request.SignupRequest;
import com.sparta.b2c.member.dto.response.SignupResponse;
import com.sparta.b2c.member.service.B2CMemberAuthService;
import com.sparta.common.annotation.CheckLogin;
import com.sparta.common.enums.Role;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/b2c-members")
@RequiredArgsConstructor
public class B2CMemberController {

    private final B2CMemberAuthService b2CMemberAuthService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody @Valid SignupRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(b2CMemberAuthService.signup(request));

    }

    @CheckLogin(role = Role.B2C)
    @PostMapping("/login")
    public ResponseEntity<Cookie> login(@RequestBody @Valid LoginRequest request, HttpServletResponse httpResponse) {

        Cookie cookie = b2CMemberAuthService.login(request);
        httpResponse.addCookie(cookie);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
