package com.sparta.admin;

import com.sparta.admin.member.dto.request.LoginRequest;
import com.sparta.admin.member.dto.request.SignupRequest;
import com.sparta.admin.member.dto.response.SignupResponse;
import com.sparta.admin.member.service.AdminMemberAuthService;
import com.sparta.common.service.SessionService;
import com.sparta.impostor.commerce.backend.common.exception.AuthenticationFailedException;
import com.sparta.impostor.commerce.backend.domain.adminMember.entity.AdminMember;
import com.sparta.impostor.commerce.backend.domain.adminMember.repository.AdminMemberRepository;
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
public class AdminMemberAuthServiceTest {

    @Mock
    private AdminMemberRepository adminMemberRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private SessionService sessionService;

    private AdminMemberAuthService adminMemberAuthService;

    @BeforeEach
    public void setUpTest() {
        adminMemberAuthService = new AdminMemberAuthService(adminMemberRepository, passwordEncoder, sessionService);
    }

    @Test
    @DisplayName("회원가입 성공")
    void signUpSuccessTest() {

        Mockito.when(adminMemberRepository.save(Mockito.any(AdminMember.class)))
                .then(returnsFirstArg());

        SignupResponse signupResponse = adminMemberAuthService.signup(new SignupRequest("admin@example.com", "123456789", "홍길동"));

        Assertions.assertEquals(signupResponse.email(), "admin@example.com");
        Assertions.assertEquals(signupResponse.name(), "홍길동");

    }

    @Test
    @DisplayName("회원가입 실패 - 기가입자인 경우")
    void signupFailureTest() {

        // Given
        String email = "admin@example.com";
        String password = "123456789";
        String name = "홍길동";

        AdminMember existingMember = AdminMember.createMember(email, password, name);

        Mockito.when(adminMemberRepository.findByEmail(email))
                .thenReturn(Optional.of(existingMember));

        // When
        SignupRequest signupRequest = new SignupRequest(email, password, name);
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class, () -> adminMemberAuthService.signup(signupRequest)
        );

        // Then
        Assertions.assertEquals("이미 가입한 회원입니다.", exception.getMessage());

    }

    @Test
    @DisplayName("로그인 성공")
    void loginSuccess() {

        // Given
        String email = "admin@example.com";
        String password = "123456789";
        String name = "홍길동";
        String sessionId = "generated-session-id";

        AdminMember existingMember = AdminMember.createMember(email, password, name);

        Mockito.when(adminMemberRepository.findByEmail(email))
                .thenReturn(Optional.of(existingMember));
        Mockito.when(passwordEncoder.matches(password, existingMember.getPassword()))
                .thenReturn(true);
        Mockito.when(sessionService.generateSession(Mockito.eq("ADMIN_SESSION"), Mockito.any()))
                .thenReturn(sessionId);

        // When
        LoginRequest loginRequest = new LoginRequest(email, password);
        Cookie cookie = adminMemberAuthService.login(loginRequest);

        // Then
        Assertions.assertNotNull(cookie);
        Assertions.assertEquals("ADMIN_SESSION", cookie.getName());
        Assertions.assertEquals(sessionId, cookie.getValue());
        Assertions.assertEquals("/", cookie.getPath());
        Assertions.assertTrue(cookie.isHttpOnly());
        Assertions.assertEquals(1800, cookie.getMaxAge());

    }

    @Test
    @DisplayName("로그인 실패 - 패스워드가 일치하지 않는 경우")
    void loginFailsPasswordDoesNotMatch() {

        // Given
        String email = "admin@example.com";
        String password = "123456789";
        String name = "홍길동";

        AdminMember existingMember = AdminMember.createMember(email, password, name);

        Mockito.when(adminMemberRepository.findByEmail(email))
                .thenReturn(Optional.of(existingMember));
        Mockito.when(passwordEncoder.matches("wrongPassword", existingMember.getPassword()))
                .thenReturn(false);

        // When
        LoginRequest loginRequest = new LoginRequest(email, "wrongPassword");
        AuthenticationFailedException exception = Assertions.assertThrows(
                AuthenticationFailedException.class, () -> adminMemberAuthService.login(loginRequest)
        );

        // Then
        Assertions.assertEquals("패스워드가 일치하지 않습니다.", exception.getMessage());

    }

    @Test
    @DisplayName("로그인 실패 - 회원정보가 존재하지 않는 경우")
    void loginFailsForNonexistentMember() {

        // Given
        String email = "admin@example.com";
        String password = "123456789";

        Mockito.when(adminMemberRepository.findByEmail(email))
                .thenReturn(Optional.empty());

        // When
        LoginRequest loginRequest = new LoginRequest(email, password);
        EntityNotFoundException exception = Assertions.assertThrows(
                EntityNotFoundException.class, () -> adminMemberAuthService.login(loginRequest)
        );

        // Then
        Assertions.assertEquals("회원정보가 존재하지 않습니다.", exception.getMessage());

    }
}
