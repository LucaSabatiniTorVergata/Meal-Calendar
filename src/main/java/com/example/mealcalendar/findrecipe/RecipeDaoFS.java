package com.example.mealcalendar.findrecipe;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecipeDaoFS implements RecipeDao {

    private static final String FILE_PATH = "ricette.txt";
    private final boolean useDemo;
    private static final Logger LOGGER = Logger.getLogger(RecipeDaoFS.class.getName());
    private final List<RecipeEntity> demoRecipes = new ArrayList<>();

    public RecipeDaoFS(boolean useDemo) {
        this.useDemo = useDemo;
        createFileIfNotExists();
    }

    private void createFileIfNotExists() {
        if (!useDemo) {
            try {
                Files.createFile(Paths.get(FILE_PATH));
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "Il file esiste già o non può essere creato");
            }
        }
    }

    @Override
    public boolean addRecipe(RecipeEntity recipe) throws IOException {
        if (useDemo) return demoRecipes.add(recipe);
        Files.write(Paths.get(FILE_PATH),
                (formatRecipe(recipe) + System.lineSeparator()).getBytes(),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        return true;
    }

    @Override
    public List<RecipeEntity> getAllRecipes() throws IOException {
        if (useDemo) return new ArrayList<>(demoRecipes);
        List<RecipeEntity> recipeList = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
        for (String line : lines) {
            RecipeEntity recipe = parseRecipe(line);
            if (recipe != null) {
                recipeList.add(recipe);
            }
        }
        return recipeList;
    }

    @Override
    public void saveAllRecipes(List<RecipeEntity> recipes) throws IOException {
        List<String> formatted = recipes.stream().map(this::formatRecipe).toList();
        Files.write(Paths.get(FILE_PATH), formatted, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
    }

    private String formatRecipe(RecipeEntity recipe) {
        return String.join(":", recipe.getRecipeName(), recipe.getTypeofDiet(), recipe.getTypeofMeal(),
                recipe.getNumIngredients(), recipe.getIngredients(), recipe.getDescription(), recipe.getAuthor());
    }

    private RecipeEntity parseRecipe(String line) {
        String[] parts = line.split(":", 7);
        return (parts.length == 7) ? RecipeEntityFactory.createRecipe(parts[0], parts[1], parts[2],
                parts[3], parts[4], parts[5], parts[6]) : null;
    }
}


