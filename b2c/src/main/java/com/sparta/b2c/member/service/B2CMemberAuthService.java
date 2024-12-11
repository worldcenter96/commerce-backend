package com.sparta.b2c.member.service;

import com.sparta.b2c.member.dto.request.SignupRequest;
import com.sparta.b2c.member.dto.response.SignupResponse;
import com.sparta.impostor.commerce.backend.domain.b2cMember.entity.B2CMember;
import com.sparta.impostor.commerce.backend.domain.b2cMember.repository.B2CMemberRepository;
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
}
