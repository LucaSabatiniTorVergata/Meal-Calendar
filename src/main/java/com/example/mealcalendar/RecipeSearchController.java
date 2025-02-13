package com.example.mealcalendar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecipeSearchController {

    private RecipeSearchFiltersBean filtri;

    RecipeDaoFS dao=new RecipeDaoFS();

    public RecipeSearchController(RecipeSearchFiltersBean filters) {
        this.filtri=filters;
    }
    public List<RecipeReturnBean> recipeExists(RecipeSearchFiltersBean bean)  {
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

    public List<RecipeReturnBean> trovaricette() throws IOException {

        List<RecipeReturnBean> result = recipeExists(filtri);

         if(result!=null){
            return result;
         }
         return null;

    }


}
