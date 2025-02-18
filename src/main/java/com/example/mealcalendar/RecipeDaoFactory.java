package com.example.mealcalendar;



public class RecipeDaoFactory {

    private static boolean useDemo = SessionManagerSLT.getInstance().getDemo();

    public static RecipeDaoFS createRecipeDao() {

        return new RecipeDaoFS(useDemo);
    }
}
