package com.firstsnow.blockchef.controller;


import com.firstsnow.blockchef.dto.login.LoginRequest;
import com.firstsnow.blockchef.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String token = loginService.login(request);
        return ResponseEntity.ok(token); // 토큰을 응답 본문으로 전달
    }
}
