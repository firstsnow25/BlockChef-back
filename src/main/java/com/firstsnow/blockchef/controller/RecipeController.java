package com.firstsnow.blockchef.controller;


import com.firstsnow.blockchef.dto.recipe.RecipeDetailResponse;
import com.firstsnow.blockchef.dto.recipe.RecipeSummaryResponse;
import com.firstsnow.blockchef.dto.recipe.RecipeUpsertRequest;
import com.firstsnow.blockchef.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
@Tag(name = "레시피 관련", description = "레시피 저장, 조회, 수정, 삭제 기능")
public class RecipeController {

    private final RecipeService recipeService;

    @Operation(summary = "레시피 저장 또는 수정", description = "ID가 없으면 새 레시피를 저장하고, 있으면 기존 레시피를 수정한다.")
    @PutMapping
    public ResponseEntity<String> upsertRecipe(@RequestBody @Valid RecipeUpsertRequest request,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        String ownerEmail = userDetails.getUsername();
        String recipeId = recipeService.upsertRecipe(request, ownerEmail);
        return ResponseEntity.ok(recipeId);
    }

    @Operation(summary = "내 레시피 목록 조회", description = "로그인한 사용자의 모든 레시피 목록을 조회한다.")
    @GetMapping("/my")
    public ResponseEntity<List<RecipeSummaryResponse>> getMyRecipes(@AuthenticationPrincipal UserDetails userDetails) {
        String ownerEmail = userDetails.getUsername();
        List<RecipeSummaryResponse> recipes = recipeService.getMyRecipes(ownerEmail);
        return ResponseEntity.ok(recipes);
    }

    @Operation(summary = "레시피 상세 조회", description = "레시피 ID로 상세 정보를 조회한다.")
    @GetMapping("/{id}")
    public ResponseEntity<RecipeDetailResponse> getRecipeById(@PathVariable String id,
                                                              @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        RecipeDetailResponse recipe = recipeService.getRecipeById(id, email);
        return ResponseEntity.ok(recipe);
    }

    @Operation(summary = "레시피 삭제", description = "레시피 ID를 받아 해당 레시피를 삭제한다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable String id,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        recipeService.deleteRecipe(id, userDetails.getUsername());
        return ResponseEntity.ok("레시피가 성공적으로 삭제되었습니다.");
    }
}


