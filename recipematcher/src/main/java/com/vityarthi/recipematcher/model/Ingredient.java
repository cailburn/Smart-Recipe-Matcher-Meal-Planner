package com.vityarthi.recipematcher.model;

public class Ingredient {
    private String name;
    private String category;
    private String unit;

    // Constructor
    public Ingredient(String name, String category, String unit) {
        this.name = name;
        this.category = category;
        this.unit = unit;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }



    // ingredient will look
    @Override
    public String toString() {
        return name + " (" + category + ")";
    }
}