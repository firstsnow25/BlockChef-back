package com.firstsnow.blockchef.dto.signup;

import lombok.Getter;
import lombok.Setter;

// 요청 DTO
@Getter @Setter
public class SignupRequest {
    private String name;
    private String email;
    private String password;
    // 생성자, getter, setter
}
