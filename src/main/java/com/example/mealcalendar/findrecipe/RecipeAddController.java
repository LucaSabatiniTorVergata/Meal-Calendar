package com.example.mealcalendar.findrecipe;

import java.io.IOException;

public class RecipeAddController {

    // Inizializziamo il manager passando il DAO
    private final RecipeManagerController manager =
            new RecipeManagerController(RecipeDaoFactory.createRecipeDao());

    // Metodo per salvare una ricetta usando il manager
    public boolean salvaRicetta(AddRecipeBean bean) throws RecipeDaoException {
        String nome = bean.getRecipeName();
        String dieta = bean.getTypeofDiet();
        String pasto = bean.getTypeofMeal();
        String numingredienti = bean.getNumIngredients();
        String ingredienti = bean.getIngredients();
        String descrizione = bean.getDescription();
        String autore = bean.getAuthor();

        // Creiamo la ricetta con il Builder tramite la factory
        RecipeEntity newRecipe = RecipeEntityFactory.createRecipe(
                nome, dieta, pasto, numingredienti, ingredienti, descrizione, autore
        );

        // Usiamo il manager per aggiungere la ricetta
        return manager.addRecipe(newRecipe);
    }
}
