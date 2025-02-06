package com.example.mealcalendar;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.collections.ObservableList;

public class RecipeListViewController {

    @FXML
    private ListView<String> recipeListView;

    public void updateRecipeList() {
        if (recipeListView == null) {
            System.out.println("Errore: recipeListView è null! Verifica il caricamento della FXML.");
            return;
        }

    }
}
