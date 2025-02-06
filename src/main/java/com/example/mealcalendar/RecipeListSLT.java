package com.example.mealcalendar;

import java.util.ArrayList;
import java.util.List;

public class RecipeListSLT {

    private List<RecipeEntity> listaricette;
    private static RecipeListSLT instance=null;


    public RecipeListSLT() {}

    public static RecipeListSLT getInstance() {
        if (instance == null) {
            instance = new RecipeListSLT();
        }
        return instance;
    }

    public void prendiricetta(RecipeEntity ricetta) {


    }



}
