package com.firstsnow.blockchef.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "내 정보 수정 요청")
public class MyInfoUpdateRequest {

    @Schema(description = "변경할 이름", example = "김도엽")
    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @Schema(description = "변경할 비밀번호 (선택)", example = "newPassword123!")
    private String password;

    @Schema(description = "비밀번호 확인 (선택)", example = "newPassword123!")
    private String passwordCheck;
}
