package com.sparta.admin.member.controller;


import com.sparta.admin.member.dto.request.LoginRequest;
import com.sparta.admin.member.dto.request.SignupRequest;
import com.sparta.admin.member.dto.response.SignupResponse;
import com.sparta.admin.member.service.AdminMemberAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
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

    @PostMapping("/login")
    public ResponseEntity<ResponseCookie> login(@RequestBody @Valid LoginRequest request) {

        ResponseCookie cookie = adminMemberAuthService.login(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }
}
