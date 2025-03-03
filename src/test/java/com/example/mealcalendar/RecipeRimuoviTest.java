package com.example.mealcalendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


class RecipeRimuoviTest {

    //Luca Sabatini

    private RecipeEditController recipeEditController;
    private RecipeDaoFS recipeDao;
    public String formatRecipe(RecipeEntity recipe) {
        return String.join(":", recipe.getRecipeName(), recipe.getTypeofDiet(), recipe.getTypeofMeal(),
                recipe.getNumIngredients(), recipe.getIngredients(), recipe.getDescription(), recipe.getAuthor());
    }


    @BeforeEach
    void setUp() {
        // Usa il file system (useDemo = false) per il test
        recipeDao = new RecipeDaoFS(false);  // Inizializziamo con useDemo = false per il test con il file system
        recipeEditController = new RecipeEditController();

    }
    @Test
    void testRemoveNonExistingRecipe() throws RecipeDaoException {

        int count = recipeDao.getAllRecipes().size();
        // Proviamo a rimuovere una ricetta che non esiste
        recipeEditController.rimuovi("Non Esistente - Tipo - Pasto - 5 - Ingredienti - Descrizione - autore");

        // Non dovrebbero esserci cambiamenti nella lista
        List<RecipeEntity> recipesAfterAttemptedRemoval = recipeDao.getAllRecipes();
        assertEquals(count, recipesAfterAttemptedRemoval.size(), "La lista delle ricette non dovrebbe essere cambiata.");
    }
}
