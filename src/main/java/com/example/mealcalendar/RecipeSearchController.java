package com.example.mealcalendar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecipeSearchController {

    private RecipeSearchFiltersBean filtri;

    RecipeDaoFS dao=new RecipeDaoFS();
    RecipeAddController rc=new RecipeAddController();

    public RecipeSearchController(RecipeSearchFiltersBean filters) {
        this.filtri=filters;
    }

    public List<RecipeReturnBean> trovaricette() throws IOException {

        List<RecipeReturnBean> result=rc.recipeExists(filtri);
         if(result!=null){
            return result;
         }
         return null;

    }


}
