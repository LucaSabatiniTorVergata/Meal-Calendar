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

    public List<RecipeReturnBean> trovaricette() throws IOException {

        List<RecipeReturnBean> result=dao.recipeExists(filtri);
         if(result!=null){
            return result;
         }
         return null;

    }


}
