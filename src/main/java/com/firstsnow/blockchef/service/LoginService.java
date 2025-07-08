package com.firstsnow.blockchef.service;


import com.firstsnow.blockchef.config.JwtTokenProvider;
import com.firstsnow.blockchef.domain.user.User;
import com.firstsnow.blockchef.dto.login.LoginRequest;
import com.firstsnow.blockchef.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginService(UserRepository userRepository,
                        PasswordEncoder passwordEncoder,
                        JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String login(LoginRequest request) {
        // db 에서 이메일로 사용자 조회
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("이메일이 존재하지 않습니다."));

        // 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 토큰 발급 및 반환
        return jwtTokenProvider.createToken(user.getEmail());
    }




}

