package com.example.mealcalendar.findrecipe;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class RecipeSearchController {

    private RecipeSearchFiltersBean filtri;
    private final RecipeManagerController managerController;

    public RecipeSearchController(RecipeSearchFiltersBean filters) {
        this.filtri = filters;
        RecipeDao dao = RecipeDaoFactory.createRecipeDao();
        this.managerController = new RecipeManagerController(dao);
    }

    // Metodo per cercare ricette che corrispondono ai filtri
    public Optional<List<RecipeReturnBean>> trovaricette() throws RecipeDaoException, IOException {
        List<RecipeReturnBean> result = managerController.searchRecipesByFilters(
                filtri.getTipoDieta(),
                filtri.getTipoPasto()
        );
        return Optional.ofNullable(result);
    }
}
