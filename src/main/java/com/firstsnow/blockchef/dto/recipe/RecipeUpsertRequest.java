package com.firstsnow.blockchef.dto.recipe;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.List;

@Getter
public class RecipeUpsertRequest {

    @Schema(description = "레시피 ID (수정 시 필요, 신규 생성 시 null)", example = "64b8fda1234567")
    private String id;

    @Schema(description = "레시피 제목", example = "얼큰 감자 스튜")
    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @Schema(description = "레시피 설명", example = "매운 맛이 도는 감자 베이스 스튜입니다.")
    private String description;

    @Schema(description = "블록 구조 (Blockly JSON)", example = "{...}")
    @NotBlank(message = "블록 구조는 필수입니다.")
    private String blockStructure;

    @Schema(description = "태그 목록", example = "[\"매운맛\", \"스튜\", \"감자\"]")
    private List<String> tags;
}
