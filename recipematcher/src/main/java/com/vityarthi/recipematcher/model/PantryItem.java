package com.vityarthi.recipematcher.model;

import java.time.LocalDate;

public class PantryItem {
    private Ingredient ingredient;
    private double quantity;
    private LocalDate expiryDate;

    // Constructor
    public PantryItem(Ingredient ingredient, double quantity, LocalDate expiryDate) {
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
    }

    // Getters and Setters
    public Ingredient getIngredient() { return ingredient; }
    public void setIngredient(Ingredient ingredient) { this.ingredient = ingredient; }

    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) { this.quantity = quantity; }

    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }

    @Override
    public String toString() {
        return ingredient.getName() + " - " + quantity + " " + ingredient.getUnit() + 
               " (Expires: " + expiryDate + ")";
    }
}