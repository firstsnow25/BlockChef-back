package com.firstsnow.blockchef.service;

import com.firstsnow.blockchef.exception.ApplicationError;
import com.firstsnow.blockchef.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final UserService userService;

    private static final long VERIFICATION_CODE_TTL_MINUTES = 3;
    // 이메일 → 인증번호 저장
    private final Map<String, String> codeMap = new ConcurrentHashMap<>();

    // 이메일 → TTL 제거 스케줄 관리
    private final Map<String, ScheduledFuture<?>> ttlTasks = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void sendSignupVerificationCode(String email) {
        // 이메일 중복 되면 에러
        if (userService.checkEmailDuplicate(email)) {
            throw new ApplicationException(ApplicationError.DUPLICATE_EMAIL);
        }
        sendVerificationCode(email);
    }

    public void sendResetPasswordVerificationCode(String email) {
        // 이메일 중복 아니면 에러
        if (!userService.checkEmailDuplicate(email)) {
            throw new ApplicationException(ApplicationError.USER_NOT_FOUND_BY_EMAIL);
        }
        sendVerificationCode(email);
    }

    public void sendVerificationCode(String email) {
        String code = generateCode();
        codeMap.put(email, code);
        // TTL: 3분 후 자동 삭제
        ScheduledFuture<?> task = scheduler.schedule(() -> {
            codeMap.remove(email);  // 인증 코드 삭제
            ttlTasks.remove(email); // TTL 작업 자체도 삭제
        }, VERIFICATION_CODE_TTL_MINUTES, TimeUnit.MINUTES);

        ttlTasks.put(email, task);
        sendMail(email, code);
    }

    public void verifyCodeOrThrow(String email, String inputCode) {
        String savedCode = codeMap.get(email);

        if (savedCode == null) {
            throw new ApplicationException(ApplicationError.VERIFICATION_CODE_NOT_FOUND);
        }
        if (!inputCode.equals(savedCode)) {
            throw new ApplicationException(ApplicationError.VERIFICATION_CODE_MISMATCH);
        }
        codeMap.remove(email);
        ScheduledFuture<?> task = ttlTasks.remove(email);
        if (task != null) {
            task.cancel(false);
        }
    }

    private String generateCode() {
        return String.valueOf((int)(Math.random() * 900000) + 100000); // 6자리
    }

    private void sendMail(String recipientEmail, String code) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(recipientEmail);
            message.setSubject("[BlockChef] 이메일 인증번호");
            message.setText(String.format("인증번호: %s\n%d분 내에 입력해주세요.", code, VERIFICATION_CODE_TTL_MINUTES));
            mailSender.send(message);
        } catch (Exception e) {
            throw new ApplicationException(ApplicationError.EMAIL_SEND_FAILED);
        }
    }
}
