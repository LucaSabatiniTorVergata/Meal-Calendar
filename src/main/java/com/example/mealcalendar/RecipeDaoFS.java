package com.example.mealcalendar;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeDaoFS implements RecipeDao {

    private static final String FILE_PATH = "ricette.txt";
    private static boolean USEDEMO = false; //di base non usa la demo

    // Lista per memorizzare le ricette in modalità demo
    private static List<RecipeEntity> demoRecipes = new ArrayList<>();

    public RecipeDaoFS(boolean useDemo) {
        USEDEMO = useDemo;
        createFileIfNotExists();
    }

    private void createFileIfNotExists() {
        File file = new File(FILE_PATH);
        if (!file.exists() && !USEDEMO) {  // Se non è in modalità demo, crea il file
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean addRecipe(RecipeEntity recipe) {
        if (USEDEMO) {
            return addRecipeDemo(recipe); // Usa la lista demo in memoria
        } else {
            return addRecipeFS(recipe);  // Usa il file system
        }
    }

    private boolean addRecipeFS(RecipeEntity recipe) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(formatRecipe(recipe));
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean addRecipeDemo(RecipeEntity recipe) {
        demoRecipes.add(recipe);  // Aggiunge la ricetta alla lista in memoria
        return true;
    }

    public List<RecipeEntity> getAllRecipes() {
        if (USE_DEMO) {
            return getAllRecipesDemo(); // Restituisce la lista demo
        } else {
            return getAllRecipesFS();  // Restituisce le ricette dal file
        }
    }

    private List<RecipeEntity> getAllRecipesFS() {
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

    private List<RecipeEntity> getAllRecipesDemo() {
        return new ArrayList<>(demoRecipes);  // Restituisce una copia della lista demo
    }

    private RecipeEntity parseRecipe(String line) {
        String[] parts = line.split(":", 7);
        if (parts.length < 7) return null; // Verifica che la riga sia completa
        return new RecipeEntity(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]);
    }

    public void updateRecipe(RecipeEntity oldRecipe, RecipeEntity newRecipe) throws IOException {
        if (USEDEMO) {
            updateRecipeDemo(oldRecipe, newRecipe); // Usa la lista demo per aggiornare
        } else {
            updateRecipeFS(oldRecipe, newRecipe);  // Usa il file system per aggiornare
        }
    }

    private void updateRecipeFS(RecipeEntity oldRecipe, RecipeEntity newRecipe) throws IOException {
        List<RecipeEntity> recipes = getAllRecipesFS(); // Legge il file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (RecipeEntity recipe : recipes) {
                if (formatRecipe(recipe).equals(formatRecipe(oldRecipe))) {
                    writer.write(formatRecipe(newRecipe)); // Scrive la versione aggiornata
                } else {
                    writer.write(formatRecipe(recipe));
                }
                writer.newLine();
            }
        }
    }

    private void updateRecipeDemo(RecipeEntity oldRecipe, RecipeEntity newRecipe) {
        for (int i = 0; i < demoRecipes.size(); i++) {
            if (formatRecipe(demoRecipes.get(i)).equals(formatRecipe(oldRecipe))) {
                demoRecipes.set(i, newRecipe);  // Sostituisce la ricetta vecchia con quella nuova
                return;
            }
        }
    }

    public void removeRecipe(RecipeEntity recipeToRemove) throws IOException {
        if (USEDEMO) {
            removeRecipeDemo(recipeToRemove); // Usa la lista demo per rimuovere
        } else {
            removeRecipeFS(recipeToRemove);  // Usa il file system per rimuovere
        }
    }

    private void removeRecipeFS(RecipeEntity recipeToRemove) throws IOException {
        List<RecipeEntity> recipes = getAllRecipesFS(); // Legge il file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (RecipeEntity recipe : recipes) {
                if (!formatRecipe(recipe).equals(formatRecipe(recipeToRemove))) { // Salva tutte tranne quella da rimuovere
                    writer.write(formatRecipe(recipe));
                    writer.newLine();
                }
            }
        }
    }

    private void removeRecipeDemo(RecipeEntity recipeToRemove) {
        demoRecipes.removeIf(recipe -> formatRecipe(recipe).equals(formatRecipe(recipeToRemove))); // Rimuove dalla lista demo
    }

    private String formatRecipe(RecipeEntity recipe) {
        return String.join(":", recipe.getRecipeName(), recipe.getTypeofDiet(),
                recipe.getTypeofMeal(), recipe.getNumIngredients(),
                recipe.getIngredients(), recipe.getDescription(), recipe.getAuthor());
    }
}
