package com.firstsnow.blockchef.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.*;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    // 이메일 → 인증번호 저장
    private final Map<String, String> codeMap = new ConcurrentHashMap<>();

    // 이메일 → TTL 제거 스케줄 관리
    private final Map<String, ScheduledFuture<?>> ttlTasks = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationCode(String email) {
        String code = generateCode();
        codeMap.put(email, code);

        // TTL: 3분 후 자동 삭제
        ScheduledFuture<?> task = scheduler.schedule(() -> {
            codeMap.remove(email);  // 인증 코드 삭제
            ttlTasks.remove(email); // TTL 작업 자체도 삭제
        }, 3, TimeUnit.MINUTES);

        ttlTasks.put(email, task);

        sendMail(email, code);
    }

    public boolean verifyCode(String email, String inputCode) {
        String savedCode = codeMap.get(email);
        return inputCode.equals(savedCode);
    }

    private String generateCode() {
        return String.valueOf((int)(Math.random() * 900000) + 100000); // 6자리
    }

    private void sendMail(String to, String code) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("[BlockChef] 이메일 인증번호");
            message.setText("인증번호: " + code + "\n3분 내에 입력해주세요.");
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("메일 전송 실패", e);
        }
    }
}

