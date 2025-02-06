package com.example.mealcalendar;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeDaoFS {

    private static final String FILE_PATH = "ricette.txt";

    public RecipeDaoFS() {
        File file = new File(FILE_PATH);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean addRecipe(RecipeEntity recipe) throws IOException {


        if (recipeExists(recipe.getRecipeName())) {
            return false;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(recipe.getRecipeName() + ":" + recipe.getTypeofDiet() + ":" + recipe.getTypeofMeal() + ":" + recipe.getNumIngredients() + ":" + recipe.getIngredients() + ":" + recipe.getAuthor() + "\n");
        }
        return true;
    }

    public boolean recipeExists(String recipeName) throws IOException {

        List<RecipeEntity> recipeEntityList = getAllRecipes();
        for (RecipeEntity recipe : recipeEntityList) {
            if (recipe.getRecipeName().equals(recipeName)) {
                return true; // L'username esiste già
            }
        }
        return false;
    }

    private List<RecipeEntity> getAllRecipes() throws IOException {
        List<RecipeEntity> recipeEntityList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] recipeParts = line.split(":");
                recipeEntityList.add(new RecipeEntity(recipeParts[0], recipeParts[1], recipeParts[2],recipeParts[3],recipeParts[4],recipeParts[5],recipeParts[6]));
            }
        }
        return recipeEntityList;
    }



}
