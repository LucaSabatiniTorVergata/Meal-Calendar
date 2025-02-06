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
        ObservableList<RecipeEntity> recipes = RecipeAddController.getRecipeList();
        recipeListView.getItems().clear();
        for (RecipeEntity recipe : recipes) {
            recipeListView.getItems().add(recipe.getRecipeName() + " - " + recipe.getTypeofDiet() + " - " + recipe.getTypeofMeal());
        }
    }
}
