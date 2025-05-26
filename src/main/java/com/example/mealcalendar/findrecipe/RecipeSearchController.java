package com.example.mealcalendar.findrecipe;

import java.util.List;
import java.util.Optional;

public class RecipeSearchController {

    private RecipeSearchFiltersBean filtri;
    private RecipeDaoFS dao = RecipeDaoFactory.createRecipeDao();

    public RecipeSearchController(RecipeSearchFiltersBean filters) {
        this.filtri = filters;
    }

    // Metodo per cercare ricette che corrispondono ai filtri
    public Optional<List<RecipeReturnBean>> trovaricette() throws RecipeDaoException {
        List<RecipeReturnBean> result = filterRecipes(filtri);
        return Optional.ofNullable(result);
    }

    // Metodo che applica i filtri per ottenere le ricette
    private List<RecipeReturnBean> filterRecipes(RecipeSearchFiltersBean filters) throws RecipeDaoException {
        // Supponiamo che RecipeListSLT sia un singleton che gestisce la lista filtrata
        RecipeListSLT listaRicette = RecipeListSLT.getInstance();
        listaRicette.svuotaLista();

        List<RecipeEntity> recipeEntityList = dao.getAllRecipes();

        // Aggiungi ricette che corrispondono ai filtri
        for (RecipeEntity recipe : recipeEntityList) {
            if (isRecipeMatchingFilters(recipe, filters)) {
                listaRicette.aggiungiRicette(recipe);
            }
        }

        return listaRicette.getrcicettereturn();
    }

    // Metodo che verifica se una ricetta corrisponde ai filtri
    private boolean isRecipeMatchingFilters(RecipeEntity recipe, RecipeSearchFiltersBean filters) {
        return recipe.getTypeofDiet().equalsIgnoreCase(filters.getTipoDieta()) &&
                recipe.getTypeofMeal().equalsIgnoreCase(filters.getTipoPasto());
    }
}
