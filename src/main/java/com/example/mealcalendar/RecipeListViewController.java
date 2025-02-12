package com.example.mealcalendar;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecipeListViewController {

    private static final Logger LOGGER = Logger.getLogger(RecipeListViewController.class.getName());

    @FXML
    private ListView<String> recipeListView;

    public void updateRecipeList() {
        if (recipeListView == null) {
            LOGGER.log(Level.SEVERE, "Errore: recipeListView è null! Verifica il caricamento della FXML.");
            return;
        }
    }
}
