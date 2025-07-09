package com.firstsnow.blockchef.service;

import com.firstsnow.blockchef.domain.user.User;
import com.firstsnow.blockchef.dto.passwordchange.PasswordChangeRequest;
import com.firstsnow.blockchef.dto.passwordchange.PasswordChangeResponse;
import com.firstsnow.blockchef.dto.signup.SignupRequest;
import com.firstsnow.blockchef.dto.signup.SignupResponse;
import com.firstsnow.blockchef.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    public SignupResponse signup(SignupRequest request) {

        // 비밀번호 일치 여부 검증
        validatePasswordMatch(request.getPassword(), request.getPasswordCheck());

        // 새로운 사용자 생성
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // 사용자 저장
        User savedUser = userRepository.save(user);

        // 응답 생성
        SignupResponse response = new SignupResponse();
        response.setSuccess(true);
        response.setMessage("회원가입이 성공적으로 완료되었습니다.");

        return response;
    }

    // 이메일 중복 검사
    @Transactional(readOnly = true)
    public boolean checkEmailDuplicate(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private void validatePasswordMatch(String pw1, String pw2) {
        if (!pw1.equals(pw2)) {
            throw new IllegalArgumentException("비밀번호와 재입력 비밀번호가 일치하지 않습니다.");
        }
    }

    // 비밀번호 재설정
    @Transactional
    public PasswordChangeResponse resetPasswordByEmail(PasswordChangeRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("해당 이메일로 가입된 사용자가 없습니다."));

        // 비밀번호 일치 여부 검증
        validatePasswordMatch(request.getPassword(), request.getPasswordCheck());

        // 유저 비밀번호 수정 후 저장
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        PasswordChangeResponse response = new PasswordChangeResponse();
        response.setSuccess(true);
        response.setMessage("비밀번호가 성공적으로 변경되었습니다.");

        return response;
    }
}