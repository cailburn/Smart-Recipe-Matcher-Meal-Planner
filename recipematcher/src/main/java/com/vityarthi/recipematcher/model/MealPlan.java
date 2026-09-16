package com.vityarthi.recipematcher.model;

import java.time.LocalDate;

public class MealPlan {
    private LocalDate planDate;
    private String mealType;
    private Recipe recipe;

    // Constructor
    public MealPlan(LocalDate planDate, String mealType, Recipe recipe) {
        this.planDate = planDate;
        this.mealType = mealType;
        this.recipe = recipe;
    }

    // Getters and Setters
    public LocalDate getPlanDate() { return planDate; }
    public void setPlanDate(LocalDate planDate) { this.planDate = planDate; }

    public String getMealType() { return mealType; }
    public void setMealType(String mealType) { this.mealType = mealType; }

    public Recipe getRecipe() { return recipe; }
    public void setRecipe(Recipe recipe) { this.recipe = recipe; }

    @Override
    public String toString() {
        return planDate + " [" + mealType + "]: " + recipe.getTitle();
    }
}