package com.example.mealcalendar.findrecipe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecipeManagerController {

    private static final Logger LOGGER = Logger.getLogger(RecipeManagerController.class.getName());
    private final RecipeDao dao;

    public RecipeManagerController(RecipeDao dao) {
        this.dao = dao;
    }

    public boolean addRecipe(RecipeEntity recipe) throws RecipeDaoException {
        try {
            return dao.addRecipe(recipe);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Errore nella scrittura della ricetta: {0}", recipe.getRecipeName());
            try {
                Thread.sleep(2000);
                return dao.addRecipe(recipe);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                throw new RecipeDaoException("Thread interrotto durante retry per la ricetta: " + recipe.getRecipeName(), ie);
            } catch (IOException retryEx) {
                throw new RecipeDaoException("Errore durante l'aggiunta della ricetta dopo retry: " + recipe.getRecipeName(), retryEx);
            }
        }
    }


    public List<RecipeEntity> getAllRecipes() throws RecipeDaoException {
        try {
            return dao.getAllRecipes();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Lettura da file non riuscita, ritorna lista vuota", e);
            return new ArrayList<>();
        }
    }

    public void updateRecipe(RecipeEntity oldRecipe, RecipeEntity newRecipe) throws RecipeDaoException {
        try {
            List<RecipeEntity> recipes = dao.getAllRecipes();
            for (int i = 0; i < recipes.size(); i++) {
                if (recipes.get(i).equals(oldRecipe)) {
                    recipes.set(i, newRecipe);
                    break;
                }
            }
            dao.saveAllRecipes(recipes);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Errore aggiornamento ricetta: {0}", oldRecipe.getRecipeName());
            throw new RecipeDaoException("Errore aggiornamento ricetta: " + oldRecipe.getRecipeName(), e);
        }
    }

    public void removeRecipe(RecipeEntity recipeToRemove) throws RecipeDaoException {
        try {
            List<RecipeEntity> recipes = dao.getAllRecipes();
            recipes.removeIf(recipe -> recipe.equals(recipeToRemove));
            dao.saveAllRecipes(recipes);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Errore rimozione ricetta: {0}", recipeToRemove.getRecipeName());
            throw new RecipeDaoException("Errore rimozione ricetta: " + recipeToRemove.getRecipeName(), e);
        }
    }

    public List<RecipeReturnBean> searchRecipesByFilters(String tipoDieta, String tipoPasto) throws RecipeDaoException, IOException {
        List<RecipeEntity> allRecipes = dao.getAllRecipes();
        List<RecipeReturnBean> filtered = new ArrayList<>();

        for (RecipeEntity recipe : allRecipes) {
            if (recipe.getTypeofDiet().equalsIgnoreCase(tipoDieta)
                    && recipe.getTypeofMeal().equalsIgnoreCase(tipoPasto)) {

                filtered.add(new RecipeReturnBean(
                        recipe.getRecipeName(),
                        recipe.getTypeofDiet(),
                        recipe.getTypeofMeal(),
                        recipe.getNumIngredients(),
                        recipe.getIngredients(),
                        recipe.getDescription(),
                        recipe.getAuthor()
                ));
            }
        }

        return filtered;
    }

}

