package com.firstsnow.blockchef.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmailServiceTest {
    @Autowired
    private EmailService emailService;

    @Test
    void testSendEmail() {
        emailService.sendVerificationCode("nicedog02@naver.com"
        );
    }
}