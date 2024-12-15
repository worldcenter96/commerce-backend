package com.sparta.b2c.member.service;

import com.sparta.b2c.member.dto.request.LoginRequest;
import com.sparta.b2c.member.dto.request.SignupRequest;
import com.sparta.b2c.member.dto.response.SignupResponse;
import com.sparta.common.service.SessionService;
import com.sparta.impostor.commerce.backend.common.exception.AuthenticationFailedException;
import com.sparta.impostor.commerce.backend.common.exception.ForbiddenAccessException;
import com.sparta.impostor.commerce.backend.domain.b2cMember.entity.B2CMember;
import com.sparta.impostor.commerce.backend.domain.b2cMember.enums.B2CMemberStatus;
import com.sparta.impostor.commerce.backend.domain.b2cMember.repository.B2CMemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class B2CMemberAuthService {

    private final B2CMemberRepository b2CMemberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final SessionService sessionService;
    private static final String SESSION_NAME = "B2C_SESSION";

    public SignupResponse signup(SignupRequest request) {

        String email = request.email();
        String password = passwordEncoder.encode(request.password());
        String name = request.name();

        if (b2CMemberRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("이미 가입한 회원입니다.");
        }

        B2CMember createMember = B2CMember.createMember(email, password, name);
        b2CMemberRepository.save(createMember);

        return SignupResponse.from(createMember);
    }

    public Cookie login(LoginRequest request) {

        String email = request.email();
        String rawPassword = request.password();

        B2CMember member = b2CMemberRepository.findByEmail(email).orElseThrow(() ->
                new EntityNotFoundException("회원정보가 존재하지 않습니다."));

        if (!passwordEncoder.matches(rawPassword, member.getPassword())) {
            throw new AuthenticationFailedException("패스워드가 일치하지 않습니다.");
        }

        if (member.getB2cMemberStatus() == B2CMemberStatus.INACTIVE) {
            throw new ForbiddenAccessException("비활성화된 사용자 입니다. 관리자에게 연락해주세요.");
        }

        String sessionId = sessionService.generateSession(SESSION_NAME, member.getId());

        Cookie cookie = new Cookie(SESSION_NAME, sessionId);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(1800);

        return cookie;
    }
}
