package com.firstsnow.blockchef.service;

import com.firstsnow.blockchef.config.JwtTokenProvider;
import com.firstsnow.blockchef.domain.User;
import com.firstsnow.blockchef.dto.login.LoginRequest;
import com.firstsnow.blockchef.exception.ApplicationError;
import com.firstsnow.blockchef.exception.ApplicationException;
import com.firstsnow.blockchef.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public String login(LoginRequest request) {
        // db 에서 이메일로 사용자 조회
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ApplicationException(ApplicationError.LOGIN_EMAIL_NOT_FOUND));

        // 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ApplicationException(ApplicationError.LOGIN_PASSWORD_MISMATCH);
        }

        // JWT 토큰 발급 및 반환
        return jwtTokenProvider.createToken(user.getEmail());
    }
}
