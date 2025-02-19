package com.example.mealcalendar;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import static com.example.mealcalendar.MealCalenderViewBoundary.*;

public class RecipeViewBoundary {

    private static final Logger LOGGER = Logger.getLogger(RecipeViewBoundary.class.getName());

    @FXML
    private Button addrecipe;
    @FXML
    private Button editrecipe;
    @FXML
    private Button returnhome;

    @FXML
    private SplitMenuButton tipoDieta;
    @FXML
    private SplitMenuButton tipoPasto;

    @FXML
    private MenuItem omnivorous;
    @FXML
    private MenuItem vegetarian;
    @FXML
    private MenuItem vegan;

    @FXML
    private MenuItem breakfast;
    @FXML
    private MenuItem launch;
    @FXML
    private MenuItem dinner;

    @FXML
    private ListView<String> listaRicetteview;

    @FXML
    private Pane detailpane;

    @FXML
    private Label label;

    @FXML
    private Label detailLabel;

    private String tipoDietaSelezionato;
    private String pastoSelezionato;

    // Handle adding recipe view
    @FXML
    private void addrecipeview(ActionEvent event) {
        changeScene("recipeadd-view.fxml", event);
    }

    // Handle editing recipe view
    @FXML
    private void editrecipeview(ActionEvent event) {
        changeScene("recipeedit-view.fxml", event);
    }

    // Handle returning to home menu
    @FXML
    private void homeView(ActionEvent event) {
        changeScene("usermenu-view.fxml", event);
    }

    // Handle searching recipes based on selected filters
    @FXML
    private void searchrecipies(ActionEvent event) throws IOException, RecipeDaoException {
        tipoDietaSelezionato = tipoDieta.getText();
        pastoSelezionato = tipoPasto.getText();
        RecipeSearchFiltersBean bean = new RecipeSearchFiltersBean(tipoDietaSelezionato, pastoSelezionato);
        RecipeSearchController controller = new RecipeSearchController(bean);

        // Using Optional for handling possible null results
        Optional<List<RecipeReturnBean>> ricettereturnbean = controller.trovaricette();

        // If results are present, display them; otherwise, show a label
        ricettereturnbean.ifPresentOrElse(
                this::mostraricette,
                () -> {
                    listaRicetteview.getItems().clear();
                    label.setVisible(true);
                }
        );
    }

    // Initialize menu actions for diet and meal types
    @FXML
    public void initialize() {
        vegan.setOnAction(e -> tipoDieta.setText("Vegan"));
        vegetarian.setOnAction(e -> tipoDieta.setText("Vegetarian"));
        omnivorous.setOnAction(e -> tipoDieta.setText("Omnivorous"));

        breakfast.setOnAction(e -> tipoPasto.setText("Breakfast"));
        launch.setOnAction(e -> tipoPasto.setText("Lunch"));
        dinner.setOnAction(e -> tipoPasto.setText("Dinner"));
    }

    // Display the list of recipes
    public void mostraricette(List<RecipeReturnBean> listaRicette) {
        listaRicetteview.getItems().clear();
        for (RecipeReturnBean ricetta : listaRicette) {
            // Using Optional to avoid null checks
            String riga = buildRecipeString(ricetta);
            LOGGER.info(riga);
            listaRicetteview.getItems().add(riga);
        }
    }

    // Handle recipe item click (double click)
    @FXML
    private void handleItemClick(MouseEvent event) throws Exception {
        if (event.getClickCount() == 2) {
            Optional.ofNullable(listaRicetteview.getSelectionModel().getSelectedItem())
                    .ifPresent(selectedItem -> {
                        try {
                            if (isVengoDaCalendar()) {
                                setVengoDaCalendar(false);
                                setRicettascelta(selectedItem);
                                MealCalenderViewBoundary.inviomail();
                                loadMealCalendarView();
                            } else {
                                showRecipeDetails(selectedItem);
                            }
                        } catch (Exception e) {
                            LOGGER.severe("Error while handling item click: " + e.getMessage());
                        }
                    });
        }
    }

    // Handle back button in recipe details view
    @FXML
    private void handleback() {
        detailpane.setVisible(false);
        listaRicetteview.setVisible(true);
    }

    // Helper method to change scenes
    private void changeScene(String fxmlFile, ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        GraphicController.cambiascena(stage, fxmlFile);
    }

    // Helper method to build recipe string for display using Optional
    private String buildRecipeString(RecipeReturnBean ricetta) {
        String nomeRicetta = Optional.ofNullable(ricetta.getRecipeName()).orElse("Unknown Recipe");
        String tipodieta = Optional.ofNullable(ricetta.getTypeofDiet()).orElse("Unknown Diet");
        String tipopasto = Optional.ofNullable(ricetta.getTypeofMeal()).orElse("Unknown Meal");
        String numingredienti = Optional.ofNullable(ricetta.getNumIngredients()).orElse("N/A");
        String ingredienti = Optional.ofNullable(ricetta.getIngredients()).orElse("No ingredients listed");
        String descrizione = Optional.ofNullable(ricetta.getDescription()).orElse("No description available");
        String author = Optional.ofNullable(ricetta.getAuthor()).orElse("Unknown Author");

        return nomeRicetta + " - " + tipodieta + " - " + tipopasto
                + " - " + numingredienti + " - " + ingredienti + " - " + descrizione + " - " + author;
    }

    // Helper method to load the Meal Calendar view
    private void loadMealCalendarView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mealcalendar-view.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) returnhome.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    // Show recipe details in a separate pane
    private void showRecipeDetails(String selectedItem) {
        detailpane.setVisible(true);
        listaRicetteview.setVisible(false);
        detailLabel.setText(selectedItem);
    }
}
