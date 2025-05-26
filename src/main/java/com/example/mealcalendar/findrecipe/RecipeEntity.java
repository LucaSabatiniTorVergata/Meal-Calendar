package com.example.mealcalendar.findrecipe;

public class RecipeEntity {
    private String recipeName;
    private String typeofDiet;
    private String typeofMeal;
    private String numIngredients;
    private String ingredients;
    private String description;
    private String author;

    // Costruttore privato, sarà usato dal Builder
    private RecipeEntity(RecipeEntityBuilder builder) {
        this.recipeName = builder.recipeName;
        this.typeofDiet = builder.typeofDiet;
        this.typeofMeal = builder.typeofMeal;
        this.numIngredients = builder.numIngredients;
        this.ingredients = builder.ingredients;
        this.description = builder.description;
        this.author = builder.author;
    }

    // Classe statica del Builder
    public static class RecipeEntityBuilder {
        private String recipeName;
        private String typeofDiet;
        private String typeofMeal;
        private String numIngredients;
        private String ingredients;
        private String description;
        private String author;

        // Metodi di configurazione per ogni campo
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

        // Costruisci l'oggetto RecipeEntity
        public RecipeEntity build() {
            return new RecipeEntity(this);
        }
    }

    // Getter per tutti i campi
    public String getRecipeName() { return recipeName; }
    public String getTypeofDiet() { return typeofDiet; }
    public String getTypeofMeal() { return typeofMeal; }
    public String getNumIngredients() { return numIngredients; }
    public String getIngredients() { return ingredients; }
    public String getDescription() { return description; }
    public String getAuthor() { return author; }
}

