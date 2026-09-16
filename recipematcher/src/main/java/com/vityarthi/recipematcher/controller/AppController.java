package com.vityarthi.recipematcher.controller;

import com.vityarthi.recipematcher.model.Ingredient;
import com.vityarthi.recipematcher.model.Recipe;
import com.vityarthi.recipematcher.repository.PantryRepository;
import com.vityarthi.recipematcher.repository.RecipeRepository;
import com.vityarthi.recipematcher.service.RecipeMatcherService;

import java.util.Scanner;

public class AppController {
    private PantryRepository pantryRepo;
    private RecipeRepository recipeRepo;
    private RecipeMatcherService matcherService;
    private Scanner scanner;

    public AppController() {
        pantryRepo = new PantryRepository();
        recipeRepo = new RecipeRepository();
        matcherService = new RecipeMatcherService();
        scanner = new Scanner(System.in);
        seedDummyData();
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Smart Recipe Matcher ===");
            System.out.println("1. View Pantry");
            System.out.println("2. Add Ingredient to Pantry");
            System.out.println("3. View Recipe Catalog");
            System.out.println("4. Find Matches for my Pantry");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("\n[Your Pantry]: " + pantryRepo.getAllItems());
                    break;
                case 2:
                    addIngredientMenu();
                    break;
                case 3:
                    System.out.println("\n[Recipe Catalog]: " + recipeRepo.getAllRecipes());
                    break;
                case 4:
                    findMatchesMenu();
                    break;
                case 5:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void addIngredientMenu() {
        System.out.print("Enter ingredient name: ");
        String name = scanner.nextLine();
        System.out.print("Enter category (e.g., Produce, Dairy): ");
        String category = scanner.nextLine();
        System.out.print("Enter unit (e.g., pieces, ml, grams): ");
        String unit = scanner.nextLine();

        pantryRepo.addIngredient(new Ingredient(name, category, unit));
        System.out.println(name + " added to your pantry!");
    }

    private void findMatchesMenu() {
        System.out.println("\n--- Matching Recipes ---");
        for (Recipe recipe : recipeRepo.getAllRecipes()) {
            double score = matcherService.calculateMatchPercentage(pantryRepo.getAllItems(), recipe);
            System.out.println("Recipe: " + recipe.getTitle() + " | Match: " + score + "%");
            if (score < 100) {
                System.out.println("   Missing: " + matcherService.getMissingIngredients(pantryRepo.getAllItems(), recipe));
            }
        }
    }

    private void seedDummyData() {
        pantryRepo.addIngredient(new Ingredient("Tomato", "Produce", "pieces"));
        pantryRepo.addIngredient(new Ingredient("Onion", "Produce", "pieces"));
        pantryRepo.addIngredient(new Ingredient("Garlic", "Produce", "cloves"));
        pantryRepo.addIngredient(new Ingredient("Eggs", "Dairy", "pieces"));
        pantryRepo.addIngredient(new Ingredient("Butter", "Dairy", "grams"));
        pantryRepo.addIngredient(new Ingredient("Rice", "Grains", "cups"));
        pantryRepo.addIngredient(new Ingredient("Soy Sauce", "Pantry", "ml"));
        pantryRepo.addIngredient(new Ingredient("Chicken", "Meat", "grams"));
        pantryRepo.addIngredient(new Ingredient("Milk", "Dairy", "ml"));
        pantryRepo.addIngredient(new Ingredient("Bread", "Bakery", "slices"));

        Recipe pasta = new Recipe("Pasta Marinara", 30);
        pasta.addIngredient(new Ingredient("Tomato", "Produce", "pieces"));
        pasta.addIngredient(new Ingredient("Pasta", "Grains", "grams"));
        pasta.addIngredient(new Ingredient("Garlic", "Produce", "cloves"));
        pasta.addDietaryTag("Vegan");
        recipeRepo.saveRecipe(pasta);

        Recipe eggs = new Recipe("Scrambled Eggs", 10);
        eggs.addIngredient(new Ingredient("Eggs", "Dairy", "pieces"));
        eggs.addIngredient(new Ingredient("Butter", "Dairy", "grams"));
        eggs.addIngredient(new Ingredient("Milk", "Dairy", "ml"));
        eggs.addDietaryTag("Vegetarian");
        recipeRepo.saveRecipe(eggs);

        // Recipe 3: Chicken Fried Rice
        Recipe friedRice = new Recipe("Chicken Fried Rice", 25);
        friedRice.addIngredient(new Ingredient("Rice", "Grains", "cups"));
        friedRice.addIngredient(new Ingredient("Chicken", "Meat", "grams"));
        friedRice.addIngredient(new Ingredient("Soy Sauce", "Pantry", "ml"));
        friedRice.addIngredient(new Ingredient("Eggs", "Dairy", "pieces"));
        friedRice.addIngredient(new Ingredient("Onion", "Produce", "pieces"));
        recipeRepo.saveRecipe(friedRice);

        // Recipe 4: Grilled Cheese Sandwich
        Recipe grilledCheese = new Recipe("Grilled Cheese", 15);
        grilledCheese.addIngredient(new Ingredient("Bread", "Bakery", "slices"));
        grilledCheese.addIngredient(new Ingredient("Butter", "Dairy", "grams"));
        grilledCheese.addIngredient(new Ingredient("Cheese", "Dairy", "slices"));
        recipeRepo.saveRecipe(grilledCheese);

        // Recipe 5 Tomato Garlic Soup
        Recipe soup = new Recipe("Tomato Garlic Soup", 40);
        soup.addIngredient(new Ingredient("Tomato", "Produce", "pieces"));
        soup.addIngredient(new Ingredient("Garlic", "Produce", "cloves"));
        soup.addIngredient(new Ingredient("Onion", "Produce", "pieces"));
        soup.addDietaryTag("Vegan");
        recipeRepo.saveRecipe(soup);
    }
}