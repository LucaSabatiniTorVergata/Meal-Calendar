package com.example.mealcalendar;

public class RecipeDaoFactory {

    public static RecipeDaoFS createRecipeDao() {

        return new RecipeDaoFS();
    }
}
