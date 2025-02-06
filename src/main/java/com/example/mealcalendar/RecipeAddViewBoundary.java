package com.example.mealcalendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.mealcalendar.RecipeEntity;
import com.example.mealcalendar.RecipeEntityFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class RecipeAddViewBoundary {


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
    private MenuItem omnivorous, vegetarian, vegan;

    @FXML
    private SplitMenuButton mealMenu;

    @FXML
    private MenuItem breakfast, lunch, dinner;

    @FXML
    private ListView<String> recipeListView;

    private RecipeAddController controller = new RecipeAddController();
    private RecipeListViewController listController = new RecipeListViewController();

    @FXML
    private void initialize() {
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
    private void handleAddRecipe(ActionEvent event) throws IOException {
        String name = recipename.getText().trim();
        String diet = (dietMenu.getText().equals("Seleziona dieta")) ? "" : dietMenu.getText();
        String meal = (mealMenu.getText().equals("Seleziona pasto")) ? "" : mealMenu.getText();
        String numIngredients = (recipeNumIngredients.getText());
        String ingredients = recipeIngredients.getText().trim();
        String description = recipeDesc.getText().trim();
        String author = SessionManagerSLT.getInstance().getLoggedInUsername();
        RecipeAddController controller= new RecipeAddController();
        AddRecipeBean bean = new AddRecipeBean(name, diet, meal, numIngredients, ingredients, description, author);
        boolean result=controller.salvaricetta(bean);
    }

    @FXML
    private void homeView(ActionEvent event) throws IOException {

        Stage stage = (Stage) homeview.getScene().getWindow();
        GraphicController.cambiascena(stage, "usermenu-view.fxml");
    }


    @FXML
    private void backView(ActionEvent event) throws IOException {

        Stage stage = (Stage) backview.getScene().getWindow();
        GraphicController.cambiascena(stage, "recipe-view.fxml");
    }
}
