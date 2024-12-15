package com.sparta.admin.member.controller;


import com.sparta.admin.member.dto.request.LoginRequest;
import com.sparta.admin.member.dto.request.SignupRequest;
import com.sparta.admin.member.dto.response.SignupResponse;
import com.sparta.admin.member.service.AdminMemberAuthService;
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
@RequestMapping("/api/admin-members")
@RequiredArgsConstructor
public class AdminMemberController {

    private final AdminMemberAuthService adminMemberAuthService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody @Valid SignupRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(adminMemberAuthService.signup(request));

    }

    @CheckLogin(role = Role.ADMIN)
    @PostMapping("/login")
    public ResponseEntity<Cookie> login(@RequestBody @Valid LoginRequest request, HttpServletResponse httpResponse) {

        Cookie cookie = adminMemberAuthService.login(request);
        httpResponse.addCookie(cookie);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
