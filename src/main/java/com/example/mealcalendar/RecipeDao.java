package com.example.mealcalendar;

import java.util.List;

public interface RecipeDao {

    boolean addRecipe(RecipeEntity recipe);
    List<RecipeEntity> getAllRecipes();
}

