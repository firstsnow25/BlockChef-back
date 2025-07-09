package com.firstsnow.blockchef.dto.email;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

// 이메일 전송 요청
@Getter
public class EmailRequest {

    @Schema(description = "이메일", example = "example@gmail.com")
    @Email(message = "올바르지 않은 형식입니다.")
    @NotBlank(message = "이메일은 필수 항목입니다.")
    private String email;
}
