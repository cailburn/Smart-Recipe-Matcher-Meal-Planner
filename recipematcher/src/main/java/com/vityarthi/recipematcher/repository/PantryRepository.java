package com.vityarthi.recipematcher.repository;

import com.vityarthi.recipematcher.model.Ingredient;
import java.util.HashSet;
import java.util.Set;

public class PantryRepository {
    private Set<Ingredient> pantryStock;

    public PantryRepository() {
        this.pantryStock = new HashSet<>();
    }


    
    // CRUD Operations
    public void addIngredient(Ingredient ingredient) {
        pantryStock.add(ingredient);
    }

    public Set<Ingredient> getAllItems() {
        return pantryStock;
    }

    public void clearPantry() {
        pantryStock.clear();
    }
}