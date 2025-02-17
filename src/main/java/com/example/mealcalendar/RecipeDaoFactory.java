package com.example.mealcalendar;

import jakarta.mail.Session;

public class RecipeDaoFactory {

    private static boolean useDemo = SessionManagerSLT.getInstance().getDemo();

    public static RecipeDaoFS createRecipeDao() {

        return new RecipeDaoFS(useDemo);
    }
}
