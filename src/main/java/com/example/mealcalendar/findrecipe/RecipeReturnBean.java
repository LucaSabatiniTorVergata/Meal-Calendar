package com.example.mealcalendar.findrecipe;

public class RecipeReturnBean {
    private String recipeName;
    private String typeofDiet;
    private String typeofMeal;
    private String numIngredients;
    private String ingredients;
    private String description;
    private String author;

    public RecipeReturnBean(String recipeName, String typeofDiet, String typeofMeal,String numIngredients, String ingredients, String description,String author) {
        this.recipeName = recipeName;
        this.typeofDiet = typeofDiet;
        this.typeofMeal = typeofMeal;
        this.numIngredients = numIngredients;
        this.ingredients = ingredients;
        this.description = description;
        this.author = author;
    }

    public String getRecipeName() { return recipeName; }
    public String getTypeofDiet() { return typeofDiet; }
    public String getTypeofMeal() { return typeofMeal; }
    public String getNumIngredients() { return numIngredients; }
    public String getIngredients() { return ingredients; }
    public String getDescription() { return description; }
    public String getAuthor() {  return author;}
}
