package com.firstsnow.blockchef.dto.email;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class VerifyCodeRequest {

    @Schema(description = "이메일", example = "example@email.com")
    @Email(message = "올바르지 않은 형식입니다.")
    @NotBlank(message = "이메일은 필수 항목입니다.")
    private String email;
    @Schema(description = "인증코드", example = "123123")
    @NotNull(message = "인증번호는 필수 항목입니다.")
    private String code;
}
