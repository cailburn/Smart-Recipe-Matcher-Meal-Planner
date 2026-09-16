package com.vityarthi.recipematcher;

import com.vityarthi.recipematcher.model.Ingredient;
import com.vityarthi.recipematcher.model.Recipe;
import com.vityarthi.recipematcher.service.RecipeMatcherService;

// If you are using JUnit 5 (Jupiter), use these imports:
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecipeMatcherServiceTest {

    private RecipeMatcherService matcherService;
    private Set<Ingredient> myPantry;
    private Recipe pastaRecipe;

    @BeforeEach
    public void setUp() {
        // This runs before EVERY test to give us a fresh setup
        matcherService = new RecipeMatcherService();
        myPantry = new HashSet<>();
        
        // Setup a basic pantry
        myPantry.add(new Ingredient("Tomato", "Produce", "pieces"));
        myPantry.add(new Ingredient("Pasta", "Grains", "grams"));

        // Setup a recipe that needs Tomato, Pasta, and Garlic
        pastaRecipe = new Recipe("Pasta Marinara", 30);
        pastaRecipe.addIngredient(new Ingredient("Tomato", "Produce", "pieces"));
        pastaRecipe.addIngredient(new Ingredient("Pasta", "Grains", "grams"));
        pastaRecipe.addIngredient(new Ingredient("Garlic", "Produce", "cloves"));
    }

    @Test
    public void testCalculateMatchPercentage_PartialMatch() {
        // Pantry has Tomato & Pasta (2 items). Recipe needs Tomato, Pasta, Garlic (3 items).
        // Match should be 2/3 = 66.66...%
        double score = matcherService.calculateMatchPercentage(myPantry, pastaRecipe);
        
        // Assert that the score is approximately 66.66 (using a delta of 0.1 for double comparison)
        assertEquals(66.66, score, 0.1, "Score should be ~66.66% for a partial match");
    }

    @Test
    public void testCalculateMatchPercentage_FullMatch() {
        // Add the missing item to the pantry so it's a 100% match
        myPantry.add(new Ingredient("Garlic", "Produce", "cloves"));
        
        double score = matcherService.calculateMatchPercentage(myPantry, pastaRecipe);
        
        assertEquals(100.0, score, "Score should be 100% when all items are present");
    }

    @Test
    public void testCalculateMatchPercentage_EmptyRecipe() {
        // Recipe with no ingredients should return 0.0% to avoid division by zero
        Recipe emptyRecipe = new Recipe("Ice Water", 1);
        
        double score = matcherService.calculateMatchPercentage(myPantry, emptyRecipe);
        
        assertEquals(0.0, score, "Score should be 0.0% if recipe requires nothing");
    }

    @Test
    public void testGetMissingIngredients() {
        // Pantry has Tomato & Pasta. Recipe needs Tomato, Pasta, Garlic.
        // Missing should be just Garlic.
        List<Ingredient> missing = matcherService.getMissingIngredients(myPantry, pastaRecipe);
        
        assertEquals(1, missing.size(), "There should be exactly 1 missing ingredient");
        assertEquals("Garlic", missing.get(0).getName(), "The missing ingredient should be Garlic");
    }
}