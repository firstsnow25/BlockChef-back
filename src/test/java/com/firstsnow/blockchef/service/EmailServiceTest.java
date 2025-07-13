package com.firstsnow.blockchef.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.AssertionsKt.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class EmailServiceTest {
    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private UserService userService;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendEmail() {
        String email = "email@email.com";
        when(userService.checkEmailDuplicate(any(String.class))).thenReturn(false);
        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));

        assertThatCode(() -> emailService.sendVerificationCode(email)).doesNotThrowAnyException();
    }
}