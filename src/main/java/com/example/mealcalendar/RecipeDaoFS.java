package com.example.mealcalendar;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeDaoFS {

    private static final String FILE_PATH = "ricette.txt";

    public RecipeDaoFS() {
        createFileIfNotExists();
    }

    private void createFileIfNotExists() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean addRecipe(RecipeEntity recipe) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(formatRecipe(recipe));
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String formatRecipe(RecipeEntity recipe) {
        return String.join(":", recipe.getRecipeName(), recipe.getTypeofDiet(),
                recipe.getTypeofMeal(), recipe.getNumIngredients(),
                recipe.getIngredients(), recipe.getDescription(), recipe.getAuthor());
    }

    public List<RecipeEntity> getAllRecipes() {
        List<RecipeEntity> recipeList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                RecipeEntity recipe = parseRecipe(line);
                if (recipe != null) {
                    recipeList.add(recipe);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recipeList;
    }

    private RecipeEntity parseRecipe(String line) {
        String[] parts = line.split(":", 7);
        if (parts.length < 7) return null; // Verifica che la riga sia completa
        return new RecipeEntity(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]);
    }
}
