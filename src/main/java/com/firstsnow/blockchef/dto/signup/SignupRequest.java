package com.firstsnow.blockchef.dto.signup;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

// 요청 DTO
@Getter @Setter
public class SignupRequest {
    @NotBlank(message = "이름은 필수 항목입니다.")
    private String name;
    @Email(message = "올바르지 않은 형식입니다.")
    @NotBlank(message = "이메일은 필수 항목입니다.")
    private String email;
    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    private String password;
    // 생성자, getter, setter
}
