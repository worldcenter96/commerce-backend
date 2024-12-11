package com.sparta.b2b.member.service;

import com.sparta.b2b.member.dto.request.LoginRequest;
import com.sparta.b2b.member.dto.request.SignupRequest;
import com.sparta.b2b.member.dto.response.SignupResponse;
import com.sparta.common.dto.MemberSession;
import com.sparta.impostor.commerce.backend.domain.b2bMember.entity.B2BMember;
import com.sparta.impostor.commerce.backend.domain.b2bMember.repository.B2BMemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.util.StandardSessionIdGenerator;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional
public class B2BMemberAuthService {

    private final B2BMemberRepository b2BMemberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RedisTemplate<String, MemberSession> redisTemplate;
    private static final String SESSION_NAME = "B2B_SESSION";

    public SignupResponse signup(SignupRequest request) {

        String email = request.email();
        String password = passwordEncoder.encode(request.password());
        String name = request.name();

        if (b2BMemberRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("이미 가입한 회원입니다.");
        }

        B2BMember createMember = B2BMember.createMember(email, password, name);
        b2BMemberRepository.save(createMember);

        return SignupResponse.from(createMember);

    }

    public ResponseCookie login(LoginRequest request) {

        String email = request.email();
        String rawPassword = request.password();

        B2BMember member = b2BMemberRepository.findByEmail(email).orElseThrow(() ->
                new EntityNotFoundException("회원정보가 존재하지 않습니다."));

        if (!passwordEncoder.matches(rawPassword, member.getPassword())) {
            throw new IllegalArgumentException("패스워드가 일치하지 않습니다.");
        }

        String sessionId = new StandardSessionIdGenerator().generateSessionId();
        String sessionKey = SESSION_NAME + ":" + sessionId;
        redisTemplate.opsForValue().set(sessionKey, new MemberSession(member.getId()), 30L, TimeUnit.MINUTES);

        return ResponseCookie
                .from(SESSION_NAME, sessionId)
                .path("/")
                .httpOnly(true)
                .maxAge(1800)
                .build();

    }
}
