package com.example.mealcalendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.mealcalendar.RecipeEntity;
import com.example.mealcalendar.RecipeEntityFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class RecipeAddController {
    private static ObservableList<RecipeEntity> recipeList = FXCollections.observableArrayList();

    public void saveRecipe(String name, String diet, String meal, String numIngredientsStr, String ingredients, String description, String author, RecipeListViewController listController) {
        if (name.isEmpty() || diet.isEmpty() || meal.isEmpty() || numIngredientsStr.isEmpty() || ingredients.isEmpty() || description.isEmpty() || author.isEmpty()) {
            System.out.println("Compila tutti i campi prima di salvare la ricetta.");
            return;
        }

        double numIngredients;
        try {
            numIngredients = Double.parseDouble(numIngredientsStr);
        } catch (NumberFormatException e) {
            System.out.println("Il numero di ingredienti deve essere un valore numerico.");
            return;
        }

        RecipeEntity recipe = RecipeEntityFactory.createRecipe(name, diet, meal, numIngredients, ingredients, description, author);
        recipeList.add(recipe);
        listController.updateRecipeList();
    }

    public static ObservableList<RecipeEntity> getRecipeList() {
        return recipeList;
    }
}