package com.firstsnow.blockchef.controller;

import com.firstsnow.blockchef.dto.email.EmailRequest;
import com.firstsnow.blockchef.dto.email.VerifyCodeRequest;
import com.firstsnow.blockchef.dto.login.LoginRequest;
import com.firstsnow.blockchef.dto.passwordchange.PasswordChangeRequest;
import com.firstsnow.blockchef.dto.signup.SignupRequest;
import com.firstsnow.blockchef.service.EmailService;
import com.firstsnow.blockchef.service.LoginService;
import com.firstsnow.blockchef.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "인증 관련", description = "로그인, 이메일 인증, 회원가입, 비밀번호 재설정")
public class AuthController {

    private final EmailService emailService;
    private final UserService userService;
    private final LoginService loginService;

    // 이메일 인증코드 전송
    @PostMapping("/email/send-code")
    public ResponseEntity<String> sendCode(@RequestBody @Valid EmailRequest request) {
        emailService.sendVerificationCode(request.getEmail());
        return ResponseEntity.ok("인증번호가 전송되었습니다.");
    }

    // 이메일 인증코드 검증
    @PostMapping("/email/verify-code")
    public ResponseEntity<String> verifyCode(@RequestBody VerifyCodeRequest request) {
        emailService.verifyCodeOrThrow(request.getEmail(), request.getCode());
        return ResponseEntity.ok("이메일 인증 성공");
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid SignupRequest request) {
        userService.signup(request);
        return ResponseEntity.ok("회원가입이 성공적으로 완료되었습니다.");
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest request) {
        String token = loginService.login(request);
        return ResponseEntity.ok(token); // 토큰을 응답 본문으로 전달
    }

    // 비밀번호 재설정
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody @Valid PasswordChangeRequest request) {
        userService.resetPasswordByEmail(request);
        return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
    }
}
