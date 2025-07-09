package com.firstsnow.blockchef.dto.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginRequest {

    public LoginRequest() {}

    @Schema(description = "이메일", example = "example@gmail.com")
    private String email;
    @Schema(description = "비밀번호", example = "123456@A")
    private String password;

}
