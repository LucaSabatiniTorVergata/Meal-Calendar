package com.example.mealcalendar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecipeAddController {

    // Istanza del DAO per interagire con il file
    private RecipeDao dao = RecipeDaoFactory.createRecipeDao();

    // Metodo per salvare una ricetta
    public boolean salvaRicetta(AddRecipeBean bean) throws IOException {
        String nome = bean.getRecipeName();
        String dieta = bean.getTypeofDiet();
        String pasto = bean.getTypeofMeal();
        String numingredienti = bean.getNumIngredients();
        String ingredienti = bean.getIngredients();
        String descrizione = bean.getDescription();
        String autore = bean.getAuthor();

        RecipeEntity newRecipe = new RecipeEntity(nome, dieta, pasto, numingredienti, ingredienti, descrizione, autore);
        return dao.addRecipe(newRecipe);
    }

    // Metodo spostato nel controller: verifica la presenza di ricette in base ai filtri
    public List<RecipeReturnBean> recipeExists(RecipeSearchFiltersBean bean) throws IOException {
        // Supponiamo che RecipeListSLT sia un singleton che gestisce la lista filtrata
        RecipeListSLT listaRicette = RecipeListSLT.getInstance();
        listaRicette.svuotaLista();

        List<RecipeEntity> recipeEntityList = dao.getAllRecipes();

        for (RecipeEntity recipe : recipeEntityList) {
            // Confronta i filtri: per esempio, tipo di dieta e tipo di pasto
            if (recipe.getTypeofDiet().equalsIgnoreCase(bean.getTipoDieta()) &&
                    recipe.getTypeofMeal().equalsIgnoreCase(bean.getTipoPasto())) {
                listaRicette.aggiungiRicette(recipe);
            }
        }
        List<RecipeReturnBean> ricetteBeans = listaRicette.getrcicettereturn();
        return ricetteBeans.size() > 0 ? ricetteBeans : null;
    }
}
