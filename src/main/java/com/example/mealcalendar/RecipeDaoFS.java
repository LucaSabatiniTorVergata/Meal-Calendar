package com.example.mealcalendar;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    // Metodo per aggiungere una ricetta
    public boolean addRecipe(RecipeEntity recipe) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(
                    recipe.getRecipeName() + ":" +
                            recipe.getTypeofDiet() + ":" +
                            recipe.getTypeofMeal() + ":" +
                            recipe.getNumIngredients() + ":" +
                            recipe.getIngredients() + ":" +
                            recipe.getDescription() + ":" +
                            recipe.getAuthor() + "\n"
            );
        }
        return true;
    }

    // Metodo per ottenere tutte le ricette dal file (ora pubblico)
    public List<RecipeEntity> getAllRecipes() throws IOException {
        List<RecipeEntity> recipeEntityList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Scanner scanner = new Scanner(line);
                scanner.useDelimiter(":");  // Usa ':' come delimitatore

                if (scanner.hasNext()) {
                    String recipename = scanner.next();
                    String typediet   = scanner.hasNext() ? scanner.next() : "";
                    String typemeal   = scanner.hasNext() ? scanner.next() : "";
                    String numingredients = scanner.hasNext() ? scanner.next() : "";
                    String ingredients    = scanner.hasNext() ? scanner.next() : "";
                    String description    = scanner.hasNext() ? scanner.next() : "";
                    String author         = scanner.hasNext() ? scanner.next() : "";
                    RecipeEntity recipe = new RecipeEntity(
                            recipename, typediet, typemeal, numingredients, ingredients, description, author
                    );
                    recipeEntityList.add(recipe);
                }
                scanner.close();
            }
        }
        return recipeEntityList;
    }
}
