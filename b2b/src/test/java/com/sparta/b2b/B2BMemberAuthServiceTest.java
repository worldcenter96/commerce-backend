package com.sparta.b2b;

import com.sparta.b2b.member.dto.request.LoginRequest;
import com.sparta.b2b.member.dto.request.SignupRequest;
import com.sparta.b2b.member.dto.response.SignupResponse;
import com.sparta.b2b.member.service.B2BMemberAuthService;
import com.sparta.common.service.SessionService;
import com.sparta.impostor.commerce.backend.common.exception.AuthenticationFailedException;
import com.sparta.impostor.commerce.backend.domain.b2bMember.entity.B2BMember;
import com.sparta.impostor.commerce.backend.domain.b2bMember.repository.B2BMemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;

@ExtendWith(MockitoExtension.class)
public class B2BMemberAuthServiceTest {

    @Mock
    private B2BMemberRepository b2bMemberRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private SessionService sessionService;

    private B2BMemberAuthService b2bMemberAuthService;

    @BeforeEach
    public void setUpTest() {
        b2bMemberAuthService = new B2BMemberAuthService(b2bMemberRepository, passwordEncoder, sessionService);
    }

    @Test
    @DisplayName("회원가입 성공")
    void signUpSuccessTest() {

        Mockito.when(b2bMemberRepository.save(Mockito.any(B2BMember.class)))
                .then(returnsFirstArg());

        SignupResponse signupResponse = b2bMemberAuthService.signup(new SignupRequest("b2b@example.com", "123456789", "홍길동"));

        Assertions.assertEquals(signupResponse.email(), "b2b@example.com");
        Assertions.assertEquals(signupResponse.name(), "홍길동");

    }

    @Test
    @DisplayName("회원가입 실패 - 기가입자인 경우")
    void signupFailureTest() {

        // Given
        String email = "b2b@example.com";
        String password = "123456789";
        String name = "홍길동";

        B2BMember existingMember = B2BMember.createMember(email, password, name);

        Mockito.when(b2bMemberRepository.findByEmail(email))
                .thenReturn(Optional.of(existingMember));

        // When
        SignupRequest signupRequest = new SignupRequest(email, password, name);
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, () -> b2bMemberAuthService.signup(signupRequest)
        );

        // Then
        Assertions.assertEquals("이미 가입한 회원입니다.", exception.getMessage());

    }

    @Test
    @DisplayName("로그인 성공")
    void loginSuccess() {

        // Given
        String email = "b2b@example.com";
        String password = "123456789";
        String name = "홍길동";
        String sessionId = "generated-session-id";

        B2BMember existingMember = B2BMember.createMember(email, password, name);

        Mockito.when(b2bMemberRepository.findByEmail(email))
                .thenReturn(Optional.of(existingMember));
        Mockito.when(passwordEncoder.matches(password, existingMember.getPassword()))
                .thenReturn(true);
        Mockito.when(sessionService.generateSession(Mockito.eq("B2B_SESSION"), Mockito.any()))
                .thenReturn(sessionId);

        // When
        LoginRequest loginRequest = new LoginRequest(email, password);
        Cookie cookie = b2bMemberAuthService.login(loginRequest);

        // Then
        Assertions.assertNotNull(cookie);
        Assertions.assertEquals("B2B_SESSION", cookie.getName());
        Assertions.assertEquals(sessionId, cookie.getValue());
        Assertions.assertEquals("/", cookie.getPath());
        Assertions.assertTrue(cookie.isHttpOnly());
        Assertions.assertEquals(1800, cookie.getMaxAge());

    }

    @Test
    @DisplayName("로그인 실패 - 패스워드가 일치하지 않는 경우")
    void loginFailsPasswordDoesNotMatch() {

        // Given
        String email = "b2b@example.com";
        String password = "123456789";
        String name = "홍길동";

        B2BMember existingMember = B2BMember.createMember(email, password, name);

        Mockito.when(b2bMemberRepository.findByEmail(email))
                .thenReturn(Optional.of(existingMember));
        Mockito.when(passwordEncoder.matches("wrongPassword", existingMember.getPassword()))
                .thenReturn(false);

        // When
        LoginRequest loginRequest = new LoginRequest(email, "wrongPassword");
        AuthenticationFailedException exception = Assertions.assertThrows(
                AuthenticationFailedException.class, () -> b2bMemberAuthService.login(loginRequest)
        );

        // Then
        Assertions.assertEquals("패스워드가 일치하지 않습니다.", exception.getMessage());

    }

    @Test
    @DisplayName("로그인 실패 - 회원정보가 존재하지 않는 경우")
    void loginFailsForNonexistentMember() {

        // Given
        String email = "b2b@example.com";
        String password = "123456789";

        Mockito.when(b2bMemberRepository.findByEmail(email))
                .thenReturn(Optional.empty());

        // When
        LoginRequest loginRequest = new LoginRequest(email, password);
        EntityNotFoundException exception = Assertions.assertThrows(
                EntityNotFoundException.class, () -> b2bMemberAuthService.login(loginRequest)
        );

        // Then
        Assertions.assertEquals("회원정보가 존재하지 않습니다.", exception.getMessage());

    }
}
