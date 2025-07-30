package com.firstsnow.blockchef.service;

import com.firstsnow.blockchef.domain.User;
import com.firstsnow.blockchef.dto.passwordchange.PasswordChangeRequest;
import com.firstsnow.blockchef.dto.signup.SignupRequest;
import com.firstsnow.blockchef.dto.user.MyInfoResponse;
import com.firstsnow.blockchef.dto.user.MyInfoUpdateRequest;
import com.firstsnow.blockchef.exception.ApplicationError;
import com.firstsnow.blockchef.exception.ApplicationException;
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

    public void signup(SignupRequest request) {

        validatePasswordMatch(request.getPassword(), request.getPasswordCheck());

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);
    }

    // 이메일 중복 검사
    @Transactional(readOnly = true)
    public boolean checkEmailDuplicate(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    // 비밀번호, 비밀번호 재입력 중복 검사
    private void validatePasswordMatch(String pw1, String pw2) {
        if (!pw1.equals(pw2)) {
            throw new ApplicationException(ApplicationError.PASSWORD_MISMATCH);
        }
    }

    // 비밀번호 재설정
    @Transactional
    public void resetPasswordByEmail(PasswordChangeRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ApplicationException(ApplicationError.USER_NOT_FOUND_BY_EMAIL));

        // 비밀번호 일치 여부 검증
        validatePasswordMatch(request.getPassword(), request.getPasswordCheck());

        // 유저 비밀번호 수정 후 저장
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }

    // 내 정보 조회
    @Transactional
    public MyInfoResponse getMyInfo(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return new MyInfoResponse(user.getName(), user.getEmail());
    }


    // 내 정보 수정
    @Transactional
    public void updateMyInfo(String email, MyInfoUpdateRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        user.setName(request.getName());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            if (!request.getPassword().equals(request.getPasswordCheck())) {
                throw new ApplicationException(ApplicationError.PASSWORD_MISMATCH); // 직접 정의한 예외 사용
            }
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        userRepository.save(user); // 변경 감지로 자동 반영되지만 명시적으로 저장
    }


    // 회원 탈퇴
    public void withdraw(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        userRepository.delete(user); // 하드 삭제 방식
    }

}
