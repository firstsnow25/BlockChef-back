package com.firstsnow.blockchef.controller;

import com.firstsnow.blockchef.dto.user.MyInfoResponse;
import com.firstsnow.blockchef.dto.user.MyInfoUpdateRequest;
import com.firstsnow.blockchef.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "유저 관련", description = "내 정보 조회, 수정, 회원 탈퇴 기능")
public class UserController {

    private final UserService userService;

    // ✅ 1. 내 정보 조회
    @Operation(summary = "내 정보 조회", description = "로그인한 사용자의 이름, 이메일 정보를 조회합니다.")
    @GetMapping("/me")
    public ResponseEntity<MyInfoResponse> getMyInfo(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        MyInfoResponse response = userService.getMyInfo(email);
        return ResponseEntity.ok(response);
    }

    // ✅ 2. 내 정보 수정
    @Operation(summary = "내 정보 수정", description = "이름 또는 비밀번호를 수정합니다. 비밀번호 변경 시에는 확인값도 필요합니다.")
    @PutMapping("/me")
    public ResponseEntity<String> updateMyInfo(@AuthenticationPrincipal UserDetails userDetails,
                                               @RequestBody @Valid MyInfoUpdateRequest request) {
        String email = userDetails.getUsername();
        userService.updateMyInfo(email, request);
        return ResponseEntity.ok("내 정보가 성공적으로 수정되었습니다.");
    }

    // ✅ 3. 회원 탈퇴
    @Operation(summary = "회원 탈퇴", description = "현재 로그인한 사용자의 계정을 삭제합니다.")
    @DeleteMapping("/me")
    public ResponseEntity<String> withdraw(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        userService.withdraw(email);
        return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
    }
}
