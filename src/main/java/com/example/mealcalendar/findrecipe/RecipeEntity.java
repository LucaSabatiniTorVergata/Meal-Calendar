package com.example.mealcalendar.findrecipe;

import java.util.Objects;

public class RecipeEntity {
    private String recipeName;
    private String typeofDiet;
    private String typeofMeal;
    private String numIngredients;
    private String ingredients;
    private String description;
    private String author;

    private RecipeEntity(RecipeEntityBuilder builder) {
        this.recipeName = builder.recipeName;
        this.typeofDiet = builder.typeofDiet;
        this.typeofMeal = builder.typeofMeal;
        this.numIngredients = builder.numIngredients;
        this.ingredients = builder.ingredients;
        this.description = builder.description;
        this.author = builder.author;
    }

    public static class RecipeEntityBuilder {
        private String recipeName;
        private String typeofDiet;
        private String typeofMeal;
        private String numIngredients;
        private String ingredients;
        private String description;
        private String author;

        public RecipeEntityBuilder recipeName(String recipeName) {
            this.recipeName = recipeName;
            return this;
        }

        public RecipeEntityBuilder typeofDiet(String typeofDiet) {
            this.typeofDiet = typeofDiet;
            return this;
        }

        public RecipeEntityBuilder typeofMeal(String typeofMeal) {
            this.typeofMeal = typeofMeal;
            return this;
        }

        public RecipeEntityBuilder numIngredients(String numIngredients) {
            this.numIngredients = numIngredients;
            return this;
        }

        public RecipeEntityBuilder ingredients(String ingredients) {
            this.ingredients = ingredients;
            return this;
        }

        public RecipeEntityBuilder description(String description) {
            this.description = description;
            return this;
        }

        public RecipeEntityBuilder author(String author) {
            this.author = author;
            return this;
        }

        public RecipeEntity build() {
            return new RecipeEntity(this);
        }
    }

    public String getRecipeName() { return recipeName; }
    public String getTypeofDiet() { return typeofDiet; }
    public String getTypeofMeal() { return typeofMeal; }
    public String getNumIngredients() { return numIngredients; }
    public String getIngredients() { return ingredients; }
    public String getDescription() { return description; }
    public String getAuthor() { return author; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecipeEntity)) return false;
        RecipeEntity that = (RecipeEntity) o;
        return recipeName.equalsIgnoreCase(that.recipeName) &&
                author.equalsIgnoreCase(that.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipeName.toLowerCase(), author.toLowerCase());
    }
}
