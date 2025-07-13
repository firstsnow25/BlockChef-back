package com.firstsnow.blockchef.dto.passwordchange;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PasswordChangeRequest {

    @Schema(description = "이메일", example = "example@email.com")
    private String email;

    @Schema(description = "비밀번호", example = "123456@A")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "비밀번호는 8자 이상이며, 영문, 숫자, 특수문자를 포함해야 합니다.")
    private String password;

    @Schema(description = "비밀번호 재입력", example = "123456@A")
    @NotBlank(message = "비밀번호 재입력은 필수입니다.")
    private String passwordCheck;
}
