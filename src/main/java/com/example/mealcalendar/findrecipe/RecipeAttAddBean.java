package com.example.mealcalendar.findrecipe;

public class RecipeAttAddBean {

    private String recipeName;
    private String typeofDiet;
    private String typeofMeal;
    private double numIngredients;
    private String ingredients;
    private String description;
    private String author;

    public RecipeAttAddBean(String recipeName, String typeofDiet, String typeofMeal, double numIngredients,
                            String ingredients, String description, String author) {
        this.recipeName = recipeName;
        this.typeofDiet = typeofDiet;
        this.typeofMeal = typeofMeal;
        this.numIngredients = numIngredients;
        this.ingredients = ingredients;
        this.description = description;
        this.author = author;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getTypeofDiet() {
        return typeofDiet;
    }

    public String getTypeofMeal() {
        return typeofMeal;
    }

    public double getNumIngredients() {
        return numIngredients;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }
}
