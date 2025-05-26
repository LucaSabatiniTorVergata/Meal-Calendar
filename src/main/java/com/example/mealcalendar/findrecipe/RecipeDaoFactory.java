package com.example.mealcalendar.findrecipe;


import com.example.mealcalendar.SessionManagerSLT;

public class RecipeDaoFactory {

    private RecipeDaoFactory() {}


    private static boolean useDemo = SessionManagerSLT.getInstance().getDemo();

    public static RecipeDaoFS createRecipeDao() {

        return new RecipeDaoFS(useDemo);
    }
}
