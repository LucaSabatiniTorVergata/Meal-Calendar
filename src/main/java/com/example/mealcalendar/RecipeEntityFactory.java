package com.example.mealcalendar;

public class RecipeEntityFactory {


    private RecipeEntityFactory() {
    } // Costruttore privato per impedire istanze

    public static RecipeEntity createRecipe(String recipeName, String typeofDiet, String typeofMeal,
                                            double numIngredients, String ingredients,
                                            String description,String author) {
        return new RecipeEntity(recipeName, typeofDiet, typeofMeal, numIngredients, ingredients, description,author);
    }
}
