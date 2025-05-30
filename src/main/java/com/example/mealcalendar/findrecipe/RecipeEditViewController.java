package com.example.mealcalendar.findrecipe;


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.mealcalendar.GraphicController;
import com.example.mealcalendar.SessionManagerSLT;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class RecipeEditViewController {

    @FXML
    private Label welcomelabel;
    @FXML
    private Button recipeEdit2;
    @FXML
    private Button ritorno;
    @FXML
    private ListView<String> ricetteview;
    @FXML
    private Button rimuovi;

    private String selectedRecipe;

    private static final Logger LOGGER = Logger.getLogger(RecipeEditViewController.class.getName());

    @FXML
    private void initialize() throws RecipeDaoException {
        initializeWelcomeLabel();
        loadRecipes();
        setupListViewListener();
    }

    // Inizializzazione del welcome label
    private void initializeWelcomeLabel() {
        String username = SessionManagerSLT.getInstance().getLoggedInUsername();
        if (username != null) {
            welcomelabel.setText("Hi, " + username + "!");
        }
    }

    // Caricamento delle ricette nell'interfaccia
    private void loadRecipes() throws RecipeDaoException {
        ricetteview.getItems().clear();
        String user = SessionManagerSLT.getInstance().getLoggedInUsername();
        RecipeEditBean bean = new RecipeEditBean(user);
        RecipeEditController controller = new RecipeEditController(bean);
        List<RecipeReturnBean> returnricette = controller.mostraricette();

        for (RecipeReturnBean ricetta : returnricette) {
            String riga = formatRecipeForDisplay(ricetta);
            ricetteview.getItems().add(riga);
        }
    }

    // Formatta una ricetta in una stringa per visualizzazione
    private String formatRecipeForDisplay(RecipeReturnBean ricetta) {
        return ricetta.getRecipeName() + " - " + ricetta.getTypeofDiet() + " - " + ricetta.getTypeofMeal()
                + " - " + ricetta.getNumIngredients() + " - " + ricetta.getIngredients() + " - "
                + ricetta.getDescription() + " - " + ricetta.getAuthor();
    }

    // Imposta il listener per la ListView
    private void setupListViewListener() {
        ricetteview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedRecipe = newValue; // Non c'è bisogno del cast
            updateButtonsState();
        });
    }

    // Aggiorna lo stato dei pulsanti (abilita/disabilita)
    private void updateButtonsState() {
        boolean isRecipeSelected = selectedRecipe != null;
        recipeEdit2.setDisable(!isRecipeSelected);
        rimuovi.setDisable(!isRecipeSelected);
    }

    // Naviga alla vista per modificare la ricetta
    @FXML
    private void recipeedit2view(ActionEvent event) {
        showRecipeDetails();
    }

    // Naviga indietro alla vista principale delle ricette
    @FXML
    private void backview(ActionEvent event) {
        Stage stage = (Stage) ritorno.getScene().getWindow();
        GraphicController.cambiascena(stage, "recipe-view.fxml");
    }

    // Mostra i dettagli della ricetta selezionata
    private void showRecipeDetails() {
        try {
            Stage stage = (Stage) recipeEdit2.getScene().getWindow();
            GraphicController.cambiascena(stage, "recipeedit2-View.fxml" );      } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Errore durante il caricamento della vista per modificare la ricetta", e);
        }
    }

    // Rimuove la ricetta selezionata
    private void rimuoviricetta() throws  RecipeDaoException, RecipeDeletionException {
        try {
            RecipeEditController controller = new RecipeEditController();
            controller.rimuovi(selectedRecipe);
        } catch ( RecipeDaoException e) {
            throw new RecipeDeletionException("Errore durante la rimozione della ricetta", e);
        }
    }

    @FXML
    private void removeRecipe(ActionEvent event) {
        try {
            rimuoviricetta();
        } catch (RecipeDaoException | RecipeDeletionException e) {
            // Gestione specifica dell'eccezione, ad esempio log o messaggio utente
            LOGGER.severe("Errore durante la rimozione della ricetta: " + e.getMessage());
        }
    }

}
