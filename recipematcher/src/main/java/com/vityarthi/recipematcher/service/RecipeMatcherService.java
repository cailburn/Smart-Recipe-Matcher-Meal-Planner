package com.vityarthi.recipematcher.service;

import com.vityarthi.recipematcher.model.Ingredient;
import com.vityarthi.recipematcher.model.Recipe;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RecipeMatcherService {


    public double calculateMatchPercentage(Set<Ingredient> pantry, Recipe recipe) {
        if (recipe.getRequiredIngredients().isEmpty()) {
            return 0.0;
        }

        Set<String> pantryItemNames = pantry.stream()
                .map(Ingredient::getName)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        long matchingCount = recipe.getRequiredIngredients().stream()
                .map(Ingredient::getName)
                .map(String::toLowerCase)
                .filter(pantryItemNames::contains)
                .count();

        return ((double) matchingCount / recipe.getRequiredIngredients().size()) * 100;
    }

    public List<Ingredient> getMissingIngredients(Set<Ingredient> pantry, Recipe recipe) {
        Set<String> pantryItemNames = pantry.stream()
                .map(Ingredient::getName)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        return recipe.getRequiredIngredients().stream()
                .filter(req -> !pantryItemNames.contains(req.getName().toLowerCase()))
                .collect(Collectors.toList());
    }
}