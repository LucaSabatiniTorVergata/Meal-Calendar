package com.example.mealcalendar;

import java.util.List;

public interface RecipeDao {

    boolean addRecipe(RecipeEntity recipe) throws RecipeDaoException;

    List<RecipeEntity> getAllRecipes() throws RecipeDaoException;

    void updateRecipe(RecipeEntity oldRecipe, RecipeEntity newRecipe) throws RecipeDaoException;

    void removeRecipe(RecipeEntity recipeToRemove) throws RecipeDaoException;
}
