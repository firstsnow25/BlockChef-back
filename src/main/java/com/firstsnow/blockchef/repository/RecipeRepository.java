package com.firstsnow.blockchef.repository;

import com.firstsnow.blockchef.domain.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends MongoRepository<Recipe, String> {

    List<Recipe> findByOwnerEmail(String ownerEmail);
}
