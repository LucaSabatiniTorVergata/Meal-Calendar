package com.example.mealcalendar;

import java.util.List;

public class RecipeEditController {

    private RecipeEditBean bean;

    public RecipeEditController(RecipeEditBean bean) {
        this.bean = bean;
    }

    public List<RecipeEntity> mostraricette(){
        RecipeDaoFS dao = RecipeDaoFactory.createRecipeDao();
        List<RecipeEntity> ricette= dao.getAllRecipes();


    }

    public List<RecipeEntity> ricettefiltro(){


    }
}
