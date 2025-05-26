package com.example.mealcalendar.findrecipe;

import com.example.mealcalendar.GraphicController;
import com.example.mealcalendar.SessionManagerSLT;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;


public class RecipeAddViewController {

    @FXML
    private Label welcomelabel;

    @FXML
    private Button backview;
    @FXML
    private Button homeview;

    @FXML
    private TextField recipeDesc;

    @FXML
    private TextField recipeIngredients;

    @FXML
    private TextField recipeNumIngredients;

    @FXML
    private TextField recipename;

    @FXML
    private SplitMenuButton dietMenu;

    @FXML
    private MenuItem omnivorous;

    @FXML
    private MenuItem vegan;

    @FXML
    private MenuItem vegetarian;

    @FXML
    private SplitMenuButton mealMenu;

    @FXML
    private MenuItem breakfast;

    @FXML
    private MenuItem lunch;

    @FXML
    private MenuItem dinner;

    @FXML
    private ListView<String> recipeListView;


    @FXML
    private void initialize() {

        String username = SessionManagerSLT.getInstance().getLoggedInUsername();
        if (username != null) {
            welcomelabel.setText("Hi, " + username + "!");
        }

        // Assicura che quando selezioni un elemento, il testo venga aggiornato
        omnivorous.setOnAction(e -> dietMenu.setText(omnivorous.getText()));
        vegetarian.setOnAction(e -> dietMenu.setText(vegetarian.getText()));
        vegan.setOnAction(e -> dietMenu.setText(vegan.getText()));

        breakfast.setOnAction(e -> mealMenu.setText(breakfast.getText()));
        lunch.setOnAction(e -> mealMenu.setText(lunch.getText()));
        dinner.setOnAction(e -> mealMenu.setText(dinner.getText()));

        // Imposta un valore predefinito per evitare null
        dietMenu.setText("Seleziona dieta");
        mealMenu.setText("Seleziona pasto");
    }

    @FXML
    private void handleAddRecipe(ActionEvent event) throws RecipeDaoException, IOException {
        String name = recipename.getText().trim();
        String diet = (dietMenu.getText().equals("Seleziona dieta")) ? "" : dietMenu.getText();
        String meal = (mealMenu.getText().equals("Seleziona pasto")) ? "" : mealMenu.getText();
        String numIngredients = (recipeNumIngredients.getText());
        String ingredients = recipeIngredients.getText().trim();
        String description = recipeDesc.getText().trim();
        String author = SessionManagerSLT.getInstance().getLoggedInUsername();
        RecipeAddController controller= new RecipeAddController();
        AddRecipeBean bean = new AddRecipeBean(name, diet, meal, numIngredients, ingredients, description, author);
        controller.salvaRicetta(bean);
    }

    @FXML
    private void homeView(ActionEvent event) {

        Stage stage = (Stage) homeview.getScene().getWindow();
        GraphicController.cambiascena(stage, "usermenu-view.fxml");
    }


    @FXML
    private void backView(ActionEvent event) {

        Stage stage = (Stage) backview.getScene().getWindow();
        GraphicController.cambiascena(stage, "recipe-view.fxml");
    }
}
