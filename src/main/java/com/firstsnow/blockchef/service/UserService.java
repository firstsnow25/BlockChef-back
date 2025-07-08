package com.firstsnow.blockchef.service;

import com.firstsnow.blockchef.domain.user.User;
import com.firstsnow.blockchef.dto.signup.SignupRequest;
import com.firstsnow.blockchef.dto.signup.SignupResponse;
import com.firstsnow.blockchef.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public SignupResponse signup(SignupRequest request) {

        // 새로운 사용자 생성
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // 사용자 저장
        User savedUser = userRepository.save(user);

        // 응답 생성
        SignupResponse response = new SignupResponse();
        if (isUserExists(savedUser)) {
            response.setSuccess(true);
            response.setMessage("회원가입이 성공적으로 완료되었습니다.");
        }
        else {
            response.setSuccess(false);
            response.setMessage("회원가입이 실패했습니다.");
        }

        return response;
    }

    // db에 유저가 제대로 생성되었는지 id로 검사
    @Transactional(readOnly = true)
    public boolean isUserExists(User user) {
        if (user == null || user.getId() == null) {
            return false;
        }
        return userRepository.findById(user.getId()).isPresent();
    }

    // 이메일 중복 검사
    @Transactional(readOnly = true)
    public boolean checkEmailDuplicate(String email) {
        return userRepository.findByEmail(email).isPresent();
    }



}