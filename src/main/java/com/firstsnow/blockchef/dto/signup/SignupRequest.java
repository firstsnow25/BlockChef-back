package com.firstsnow.blockchef.dto.signup;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

// 요청 DTO : 클라이언트의 회원가입 요청 데이터(이름, 이메일, 비밀번호)를 담는 객체
@Getter @Setter
public class SignupRequest {
    @Schema(description = "이름", example = "홍길동")
    @NotBlank(message = "이름은 필수 항목입니다.")
    private String name;

    @Schema(description = "이메일", example = "example@email.com")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @NotBlank(message = "이메일은 필수 항목입니다.")
    private String email;

    @Schema(description = "비밀번호", example = "123456A@")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "비밀번호는 8자 이상이며, 영문, 숫자, 특수문자를 포함해야 합니다.")
    private String password;

    @Schema(description = "비밀번호 재입력", example = "123456A@")
    @NotBlank(message = "비밀번호 재입력은 필수입니다.")
    private String passwordCheck;
}
