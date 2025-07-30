package com.firstsnow.blockchef.dto.recipe;

import com.firstsnow.blockchef.domain.Recipe;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class RecipeDetailResponse {

    @Schema(description = "레시피 ID", example = "64b8fda1234567")
    private String id;

    @Schema(description = "레시피 제목", example = "얼큰 감자 스튜")
    private String title;

    @Schema(description = "레시피 설명", example = "매운 맛이 도는 감자 베이스 스튜입니다.")
    private String description;

    @Schema(description = "블록 구조 (Blockly JSON)", example = "{...}")
    private String blockStructure;

    @Schema(description = "태그 목록", example = "[\"매운맛\", \"스튜\", \"감자\"]")
    private List<String> tags;

    @Schema(description = "수정된 시간", example = "2025-07-23T10:25:00")
    private LocalDateTime updatedAt;

    public static RecipeDetailResponse from(Recipe recipe) {
        return new RecipeDetailResponse(
                recipe.getId(),
                recipe.getTitle(),
                recipe.getDescription(),
                recipe.getBlockStructure(),
                recipe.getTags(),
                recipe.getUpdatedAt()
        );
    }
}
