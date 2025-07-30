package com.firstsnow.blockchef.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "내 정보 조회 응답")
public class MyInfoResponse {

    @Schema(description = "사용자의 이름", example = "홍길동")
    private String name;

    @Schema(description = "사용자의 이메일", example = "hong@blockchef.store")
    private String email;
}