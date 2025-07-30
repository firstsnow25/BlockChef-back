package com.firstsnow.blockchef.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ApplicationError {

    // 이메일 관련
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 가입된 이메일입니다."),
    EMAIL_SEND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "이메일 전송에 실패했습니다."),

    // 인증번호 관련
    VERIFICATION_CODE_NOT_FOUND(HttpStatus.BAD_REQUEST, "인증번호가 존재하지 않거나 만료되었습니다."),
    VERIFICATION_CODE_MISMATCH(HttpStatus.BAD_REQUEST, "인증번호가 일치하지 않습니다."),

    // 회원가입
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "비밀번호와 재입력 비밀번호가 일치하지 않습니다."),

    // 로그인
    LOGIN_EMAIL_NOT_FOUND(HttpStatus.UNAUTHORIZED, "이메일이 존재하지 않습니다."),
    LOGIN_PASSWORD_MISMATCH(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    USER_NOT_FOUND_BY_EMAIL(HttpStatus.NOT_FOUND, "해당 이메일로 가입된 사용자가 없습니다."),

    // recipe 관련
    RECIPE_NOT_FOUND(HttpStatus.NOT_FOUND, "레시피를 찾을 수 없습니다."),
    RECIPE_NO_PERMISSION(HttpStatus.FORBIDDEN, "레시피에 대한 권한이 없습니다.");




    private final HttpStatus status;
    private final String message;
}

