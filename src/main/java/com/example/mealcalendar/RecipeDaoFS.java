package com.example.mealcalendar;

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

    public boolean addRecipe(RecipeEntity recipe) throws RecipeDaoException {
        return useDemo ? demoRecipes.add(recipe) : addRecipeFS(recipe);
    }

    private boolean addRecipeFS(RecipeEntity recipe) throws RecipeDaoException {
        try {
            Files.write(Paths.get(FILE_PATH),
                    (formatRecipe(recipe) + System.lineSeparator()).getBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            return true;
        } catch (IOException e) {
            return handleWriteError(recipe, e);
        }
    }

    public List<RecipeEntity> getAllRecipes() throws RecipeDaoException {
        return useDemo ? new ArrayList<>(demoRecipes) : getAllRecipesFS();
    }

    private List<RecipeEntity> getAllRecipesFS() {
        List<RecipeEntity> recipeList = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            for (String line : lines) {
                RecipeEntity recipe = parseRecipe(line);
                if (recipe != null) {
                    recipeList.add(recipe);
                }
            }
        } catch (IOException e) {
            return handle();
        }
        return recipeList;
    }

    private RecipeEntity parseRecipe(String line) {
        String[] parts = line.split(":", 7);
        return (parts.length == 7) ? RecipeEntityFactory.createRecipe(parts[0], parts[1], parts[2],
                parts[3], parts[4], parts[5], parts[6]) : null;
    }

    public void updateRecipe(RecipeEntity oldRecipe, RecipeEntity newRecipe) throws RecipeDaoException {
        if (useDemo) {
            demoRecipes.replaceAll(recipe -> formatRecipe(recipe).equals(formatRecipe(oldRecipe)) ? newRecipe : recipe);
        } else {
            List<RecipeEntity> recipes = getAllRecipesFS();
            List<String> updatedRecipes = new ArrayList<>();
            for (RecipeEntity recipe : recipes) {
                if (formatRecipe(recipe).equals(formatRecipe(oldRecipe))) {
                    updatedRecipes.add(formatRecipe(newRecipe));
                }
            }
            try {
                Files.write(Paths.get(FILE_PATH), updatedRecipes, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Errore nell''aggiornamento della ricetta con il nome: {0}", oldRecipe.getRecipeName());
                throw new RecipeDaoException("Errore durante l''aggiornamento della ricetta con nome: " + oldRecipe.getRecipeName(), e);
            }
        }
    }

    public void removeRecipe(RecipeEntity recipeToRemove) throws RecipeDaoException {
        if (useDemo) {
            demoRecipes.removeIf(recipe -> formatRecipe(recipe).equals(formatRecipe(recipeToRemove)));
        } else {
            List<RecipeEntity> recipes = getAllRecipesFS();
            List<String> updatedRecipes = new ArrayList<>();
            for (RecipeEntity recipe : recipes) {
                if (!formatRecipe(recipe).equals(formatRecipe(recipeToRemove))) {
                    updatedRecipes.add(formatRecipe(recipe));
                }
            }
            try {
                Files.write(Paths.get(FILE_PATH), updatedRecipes, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Errore nella rimozione della ricetta con il nome: {0}", recipeToRemove.getRecipeName());
                throw new RecipeDaoException("Errore durante la rimozione della ricetta con nome: " + recipeToRemove.getRecipeName(), e);
            }
        }
    }

    private String formatRecipe(RecipeEntity recipe) {
        return String.join(":", recipe.getRecipeName(), recipe.getTypeofDiet(), recipe.getTypeofMeal(),
                recipe.getNumIngredients(), recipe.getIngredients(), recipe.getDescription(), recipe.getAuthor());
    }

    private boolean handleWriteError(RecipeEntity recipe, IOException e) throws RecipeDaoException {
        LOGGER.log(Level.SEVERE, "Errore nella scrittura della ricetta: {0}", recipe.getRecipeName());

        // Proviamo a riscrivere il file una seconda volta dopo qualche secondo
        try {
            Thread.sleep(2000); // Aspetta 2 secondi prima di ritentare
            Files.write(Paths.get(FILE_PATH),
                    (formatRecipe(recipe) + System.lineSeparator()).getBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            return true;
        } catch (IOException retryException) {
            LOGGER.log(Level.SEVERE, "Secondo tentativo fallito per la ricetta: {0}", recipe.getRecipeName());


            // Lancio comunque l'eccezione per notificare il problema
            throw new RecipeDaoException("Errore durante l'aggiunta della ricetta: " + recipe.getRecipeName(), e);
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            throw new RecipeDaoException("Interruzione durante la gestione dell'errore", interruptedException);
        }
    }

    private List<RecipeEntity> handle(){
        List<RecipeEntity> recipes = new ArrayList<>();
        LOGGER.log(Level.SEVERE,"Letture da file non riuscita tornera una lista vuota");
        return recipes;
    }
}

