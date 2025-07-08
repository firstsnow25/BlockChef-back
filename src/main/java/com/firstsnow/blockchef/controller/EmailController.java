package com.firstsnow.blockchef.controller;

import com.firstsnow.blockchef.dto.email.EmailRequest;
import com.firstsnow.blockchef.dto.email.VerifyCodeRequest;
import com.firstsnow.blockchef.service.EmailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/email")
@Tag(name = "이메일", description = "이메일 코드 전송 및 확인")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send-code")
    public ResponseEntity<String> sendCode(@RequestBody EmailRequest request) {
        emailService.sendVerificationCode(request.getEmail());
        return ResponseEntity.ok("인증번호가 전송되었습니다.");
    }

    @PostMapping("/verify-code")
    public ResponseEntity<String> verifyCode(@RequestBody VerifyCodeRequest request) {
        boolean result = emailService.verifyCode(request.getEmail(), request.getCode());
        if (result) {
            return ResponseEntity.ok("인증 성공");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증 실패: 인증번호가 일치하지 않거나 만료되었습니다.");
        }
    }
}



