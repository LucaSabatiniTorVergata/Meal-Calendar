package com.example.mealcalendar.findrecipe;

import java.io.IOException;
import java.util.List;

public interface RecipeDao {
    boolean addRecipe(RecipeEntity recipe) throws IOException;
    List<RecipeEntity> getAllRecipes() throws IOException;
    void saveAllRecipes(List<RecipeEntity> recipes) throws IOException;
}

