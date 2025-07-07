package com.firstsnow.blockchef.dto.email;

import lombok.Getter;
import lombok.Setter;

@Getter
public class VerifyCodeRequest {
    private String email;
    private String code;
}
