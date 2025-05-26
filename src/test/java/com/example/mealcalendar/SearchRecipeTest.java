package com.example.mealcalendar;

import com.example.mealcalendar.findrecipe.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SearchRecipeTest {

    //Luca Sabatini

    private RecipeSearchController controller;
    private RecipeDaoFS recipeDao;

    @BeforeEach
    void setUp() throws RecipeDaoException {
        // Inizializziamo il DAO senza persistenza su file
        recipeDao = new RecipeDaoFS(true);

        // Aggiungiamo alcune ricette di test usando il Builder Pattern
        recipeDao.addRecipe(new RecipeEntity.RecipeEntityBuilder()
                .recipeName("Pasta Vegana")
                .typeofDiet("Vegan")
                .typeofMeal("Lunch")
                .numIngredients("5")
                .ingredients("pasta, pomodoro")
                .description("Una pasta leggera")
                .author("Chef Mario")
                .build());

        recipeDao.addRecipe(new RecipeEntity.RecipeEntityBuilder()
                .recipeName("Insalata Proteica")
                .typeofDiet("Keto")
                .typeofMeal("Dinner")
                .numIngredients("4")
                .ingredients("pollo, lattuga")
                .description("Un'insalata ricca")
                .author("Chef Luigi")
                .build());

        recipeDao.addRecipe(new RecipeEntity.RecipeEntityBuilder()
                .recipeName("Colazione Fit")
                .typeofDiet("Vegetarian")
                .typeofMeal("Breakfast")
                .numIngredients("3")
                .ingredients("yogurt, miele")
                .description("Perfetta per iniziare la giornata")
                .author("Chef Anna")
                .build());
    }

    @Test
    void testFilterRecipes_CorrectDietAndMealType() throws RecipeDaoException {
        RecipeSearchFiltersBean filters = new RecipeSearchFiltersBean();
        filters.setTipoDieta("Vegan");
        filters.setTipoPasto("Lunch");

        controller = new RecipeSearchController(filters);
        List<RecipeReturnBean> result = controller.trovaricette().orElseThrow();

        assertTrue(result.isEmpty(), "Dovrebbe esserci almeno una ricetta vegana per il pranzo");

        // Verifica che tutte le ricette trovate abbiano il giusto tipo di dieta e pasto
        for (RecipeReturnBean recipe : result) {
            assertEquals("Vegan", recipe.getTypeofDiet(), "La ricetta dovrebbe essere di tipo 'Vegan'");
            assertEquals("Lunch", recipe.getTypeofMeal(), "La ricetta dovrebbe essere per il pranzo");
        }
    }
}