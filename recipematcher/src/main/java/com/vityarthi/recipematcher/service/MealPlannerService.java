package com.vityarthi.recipematcher.service;

import com.vityarthi.recipematcher.model.MealPlan;
import com.vityarthi.recipematcher.model.Recipe;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MealPlannerService {
    private List<MealPlan> schedule;

    public MealPlannerService() {
        this.schedule = new ArrayList<>();
    }


    public void scheduleMeal(LocalDate date, String mealType, Recipe recipe) {
        MealPlan newPlan = new MealPlan(date, mealType, recipe);
        schedule.add(newPlan);
    }

    public List<MealPlan> getMealsForDate(LocalDate date) {
        return schedule.stream()
                .filter(plan -> plan.getPlanDate().equals(date))
                .collect(Collectors.toList());
    }

    public List<Recipe> getRecipesForDateRange(LocalDate startDate, LocalDate endDate) {
        return schedule.stream()
                .filter(plan -> !plan.getPlanDate().isBefore(startDate) && !plan.getPlanDate().isAfter(endDate))
                .map(MealPlan::getRecipe) // Extract only the Recipe object
                .collect(Collectors.toList());
    }

    public List<MealPlan> getAllPlannedMeals() {
        return schedule;
    }
}