package com.sparta.b2b.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @NotBlank(message = "이메일은 필수 입력 항목입니다.")
        @Email
        String email,
        @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
        @Size(min = 8, max = 20, message = "비밀번호는 8~20자여야 합니다.")
        String password,
        @NotBlank(message = "실명은 필수 입력 항목입니다.")
        @Size(min = 2, max = 20, message = "실명은 2~20자여야 합니다.")
        String name
) {
}
