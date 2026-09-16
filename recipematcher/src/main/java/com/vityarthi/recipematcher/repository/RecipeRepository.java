package com.vityarthi.recipematcher.repository;

import com.vityarthi.recipematcher.model.Recipe;
import java.util.ArrayList;
import java.util.List;

public class RecipeRepository {
    private List<Recipe> recipeCatalog;

    public RecipeRepository() {
        this.recipeCatalog = new ArrayList<>();
    }

    public void saveRecipe(Recipe recipe) {
        recipeCatalog.add(recipe);
    }

    public List<Recipe> getAllRecipes() {
        return recipeCatalog;
    }
}