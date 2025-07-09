package com.firstsnow.blockchef.dto.login;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginRequest {

    public LoginRequest() {}

    private String email;
    private String password;


}
