package com.vityarthi.recipematcher.model;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private String title;
    private int prepTimeMinutes;
    private List<Ingredient> requiredIngredients;
    private List<String> dietaryTags; // e.g., "Vegan", "Gluten-Free"

    // Constructor
    public Recipe(String title, int prepTimeMinutes) {
        this.title = title;
        this.prepTimeMinutes = prepTimeMinutes;
        // Initialize the lists so they are ready to use
        this.requiredIngredients = new ArrayList<>();
        this.dietaryTags = new ArrayList<>();
    }

    // Getters
    public String getTitle() { return title; }
    public int getPrepTimeMinutes() { return prepTimeMinutes; }
    public List<Ingredient> getRequiredIngredients() { return requiredIngredients; }
    public List<String> getDietaryTags() { return dietaryTags; }

    // Helper methods
    public void addIngredient(Ingredient ingredient) {
        this.requiredIngredients.add(ingredient);
    }

    public void addDietaryTag(String tag) {
        this.dietaryTags.add(tag);
    }

    @Override
    public String toString() {
        return title + " (" + prepTimeMinutes + " mins)";
    }
}