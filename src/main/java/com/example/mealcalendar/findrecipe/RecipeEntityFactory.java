package com.example.mealcalendar.findrecipe;

public class RecipeEntityFactory {

    private RecipeEntityFactory() {
    } // Costruttore privato per impedire istanze

    // Metodo per creare una ricetta usando il Builder Pattern
    public static RecipeEntity createRecipe(String recipeName, String typeofDiet, String typeofMeal,
                                            String numIngredients, String ingredients,
                                            String description, String author) {
        return new RecipeEntity.RecipeEntityBuilder()
                .recipeName(recipeName)
                .typeofDiet(typeofDiet)
                .typeofMeal(typeofMeal)
                .numIngredients(numIngredients)
                .ingredients(ingredients)
                .description(description)
                .author(author)
                .build();
    }
}
