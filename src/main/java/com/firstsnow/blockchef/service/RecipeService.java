package com.firstsnow.blockchef.service;

import com.firstsnow.blockchef.domain.Recipe;
import com.firstsnow.blockchef.dto.recipe.RecipeDetailResponse;
import com.firstsnow.blockchef.dto.recipe.RecipeSummaryResponse;
import com.firstsnow.blockchef.dto.recipe.RecipeUpsertRequest;
import com.firstsnow.blockchef.exception.ApplicationError;
import com.firstsnow.blockchef.exception.ApplicationException;
import com.firstsnow.blockchef.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public String upsertRecipe(RecipeUpsertRequest request, String ownerEmail) {
        // 새 레시피 저장
        if (request.getId() == null || request.getId().isBlank()) {
            Recipe newRecipe = Recipe.builder()
                    .title(request.getTitle())
                    .description(request.getDescription())
                    .blockStructure(request.getBlockStructure())
                    .tags(request.getTags())
                    .ownerEmail(ownerEmail)
                    .updatedAt(LocalDateTime.now())
                    .build();

            return recipeRepository.save(newRecipe).getId();
        }

        // 기존 레시피 수정
        Recipe existing = recipeRepository.findById(request.getId())
                .orElseThrow(() -> new ApplicationException(ApplicationError.RECIPE_NOT_FOUND));

        if (!existing.getOwnerEmail().equalsIgnoreCase(ownerEmail)) {
            throw new ApplicationException(ApplicationError.RECIPE_NO_PERMISSION);
        }

        Recipe updated = Recipe.builder()
                .id(existing.getId())
                .title(request.getTitle())
                .description(request.getDescription())
                .blockStructure(request.getBlockStructure())
                .tags(request.getTags())
                .ownerEmail(existing.getOwnerEmail()) // 안전하게 보존
                .updatedAt(LocalDateTime.now())
                .build();

        return recipeRepository.save(updated).getId();
    }

    public List<RecipeSummaryResponse> getMyRecipes(String ownerEmail) {
        List<Recipe> recipes = recipeRepository.findByOwnerEmail(ownerEmail);
        return recipes.stream()
                .map(RecipeSummaryResponse::from)
                .collect(Collectors.toList());
    }

    public RecipeDetailResponse getRecipeById(String id, String currentUserEmail) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ApplicationError.RECIPE_NOT_FOUND));

        if (!recipe.getOwnerEmail().equalsIgnoreCase(currentUserEmail)) {
            throw new ApplicationException(ApplicationError.RECIPE_NO_PERMISSION);
        }

        return RecipeDetailResponse.from(recipe);
    }

    public void deleteRecipe(String id, String currentUserEmail) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ApplicationError.RECIPE_NOT_FOUND));

        if (!recipe.getOwnerEmail().equalsIgnoreCase(currentUserEmail)) {
            throw new ApplicationException(ApplicationError.RECIPE_NO_PERMISSION);
        }

        recipeRepository.deleteById(id);
    }
}
