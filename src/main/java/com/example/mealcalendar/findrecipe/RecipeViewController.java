package com.example.mealcalendar.findrecipe;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.mealcalendar.GraphicController;
import com.example.mealcalendar.seteatingtime.MealCalenderViewController;
import com.example.mealcalendar.SessionManagerSLT;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import static com.example.mealcalendar.seteatingtime.MealCalenderViewController.*;

public class RecipeViewController {

    private static final Logger LOGGER = Logger.getLogger(RecipeViewController.class.getName());

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
    private MenuItem lunch;
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

    // Aggiungi un campo DatePicker per calendar
    @FXML
    private DatePicker calendar;

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

    @FXML
    private void searchrecipies(ActionEvent event) throws IOException, RecipeDaoException {
        tipoDietaSelezionato = tipoDieta.getText();
        pastoSelezionato = tipoPasto.getText();
        RecipeSearchFiltersBean bean = new RecipeSearchFiltersBean();
        bean.setTipoDieta(tipoDietaSelezionato);
        bean.setTipoPasto(tipoDietaSelezionato);
        RecipeSearchController controller = new RecipeSearchController(bean);


        Optional<List<RecipeReturnBean>> ricettereturnbean = controller.trovaricette();

        ricettereturnbean.ifPresentOrElse(
                this::mostraricette,
                () -> {
                    listaRicetteview.getItems().clear();
                    label.setVisible(true);
                }
        );
    }

    @FXML
    public void initialize() {
        vegan.setOnAction(e -> tipoDieta.setText("vegano"));
        vegetarian.setOnAction(e -> tipoDieta.setText("vegetariano"));
        omnivorous.setOnAction(e -> tipoDieta.setText("onnivoro"));

        breakfast.setOnAction(e -> tipoPasto.setText("colazione"));
        lunch.setOnAction(e -> tipoPasto.setText("pranzo"));
        dinner.setOnAction(e -> tipoPasto.setText("cena"));

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
                            // Passa la data selezionata alla funzione
                            if (isVengoDaCalendar()) {
                                setVengoDaCalendar(false);
                                setRicettascelta(selectedItem);
                                // Qui viene passata la data selezionata dal calendar
                                LocalDate selectedDate = SessionManagerSLT.getInstance().getDatas();
                                if (selectedDate != null) {
                                    // Passa la data alla funzione che invia la mail
                                    MealCalenderViewController.inviomail();
                                    loadMealCalendarView();
                                }
                            } else {
                                showRecipeDetails(selectedItem);
                            }
                        } catch (Exception e) {
                            erroreException();
                            e.printStackTrace();
                        }
                    });
        }
    }


    private int erroreException() {

        LOGGER.log(Level.SEVERE, "impossibile catturare il doppio click dal mouse");
        return -1;
    }
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
        Stage stage = (Stage) returnhome.getScene().getWindow();
        GraphicController.cambiascena(stage, "mealcalendar-view.fxml");
    }

    // Show recipe details in a separate pane
    private void showRecipeDetails(String selectedItem) {
        detailpane.setVisible(true);
        listaRicetteview.setVisible(false);
        detailLabel.setText(selectedItem);
    }
}
