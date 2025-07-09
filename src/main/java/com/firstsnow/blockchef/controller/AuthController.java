package com.firstsnow.blockchef.controller;

import com.firstsnow.blockchef.dto.email.EmailRequest;
import com.firstsnow.blockchef.dto.email.VerifyCodeRequest;
import com.firstsnow.blockchef.dto.login.LoginRequest;
import com.firstsnow.blockchef.dto.passwordchange.PasswordChangeRequest;
import com.firstsnow.blockchef.dto.signup.SignupRequest;
import com.firstsnow.blockchef.dto.signup.SignupResponse;
import com.firstsnow.blockchef.service.EmailService;
import com.firstsnow.blockchef.service.LoginService;
import com.firstsnow.blockchef.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "인증 관련", description = "로그인, 이메일 인증, 회원가입, 비밀번호 재설정")
public class AuthController {

    private EmailService emailService;
    private UserService userService;
    private LoginService loginService;

    public AuthController(EmailService emailService, UserService userService, LoginService loginService) {
        this.emailService = emailService;
        this.userService = userService;
        this.loginService = loginService;
    }

    // 이메일 인증코드 전송
    @PostMapping("/email/send-code")
    public ResponseEntity<String> sendCode(@RequestBody EmailRequest request) {
        emailService.sendVerificationCode(request.getEmail());
        return ResponseEntity.ok("인증번호가 전송되었습니다.");
    }
    // 이메일 인증코드 검증
    @PostMapping("/email/verify-code")
    public ResponseEntity<String> verifyCode(@RequestBody VerifyCodeRequest request) {
        boolean result = emailService.verifyCode(request.getEmail(), request.getCode());
        if (result) {
            return ResponseEntity.ok("인증 성공");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증 실패: 인증번호가 일치하지 않거나 만료되었습니다.");
        }
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest request) {
        SignupResponse response = userService.signup(request);
        return ResponseEntity.ok(response);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String token = loginService.login(request);
        return ResponseEntity.ok(token); // 토큰을 응답 본문으로 전달
    }

    // 비밀번호 재설정
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordChangeRequest request) {
        userService.resetPasswordByEmail(request);
        return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
    }
}
