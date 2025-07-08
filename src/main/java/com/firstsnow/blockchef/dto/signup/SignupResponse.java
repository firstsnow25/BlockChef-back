package com.firstsnow.blockchef.dto.signup;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignupResponse {

    private boolean success; // 회원가입 성공 여부
    private String message; // 결과 메시지 (성공/실패)
}
