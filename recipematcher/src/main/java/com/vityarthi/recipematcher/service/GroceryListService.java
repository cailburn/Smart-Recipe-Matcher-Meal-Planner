package com.vityarthi.recipematcher.service;

import com.vityarthi.recipematcher.model.Ingredient;
import com.vityarthi.recipematcher.model.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GroceryListService {
    private RecipeMatcherService matcherService;

    public GroceryListService(RecipeMatcherService matcherService) {
        this.matcherService = matcherService;
    }

    
    public List<Ingredient> generateWeeklyShoppingList(Set<Ingredient> currentPantry, List<Recipe> plannedMeals) {
        List<Ingredient> shoppingList = new ArrayList<>();
        
        for (Recipe meal : plannedMeals) {
            List<Ingredient> missingForMeal = matcherService.getMissingIngredients(currentPantry, meal);
            // Add missing items
            shoppingList.addAll(missingForMeal);
        }
        
        return shoppingList;
    }
}