package com.example.mealcalendar;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import java.util.List;

public class RecipeViewBoundary {

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
    private MenuItem omnivorous ;
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

    private List<RecipeReturnBean> listaricette;

    private String tipoDietaSelezionato;
    private String pastoSelezionato;



    @FXML
    private void addrecipeview(ActionEvent event) {

        Stage stage = (Stage) addrecipe.getScene().getWindow();
        GraphicController.cambiascena(stage, "recipeadd-view.fxml");
    }

    @FXML
    private void editrecipeview(ActionEvent event) throws IOException {

        Stage stage = (Stage) editrecipe.getScene().getWindow();
        GraphicController.cambiascena(stage, "recipeedit-view.fxml");
    }
    @FXML
    private void homeView(ActionEvent event) throws IOException {

        Stage stage = (Stage) returnhome.getScene().getWindow();
        GraphicController.cambiascena(stage, "usermenu-view.fxml");
    }


    @FXML
    private void searchrecipies(ActionEvent event) throws IOException {

        tipoDietaSelezionato = tipoDieta.getText();
        pastoSelezionato= tipoPasto.getText();

        RecipeSearchFiltersBean bean=new RecipeSearchFiltersBean(tipoDietaSelezionato,pastoSelezionato);
        RecipeSearchController controller=new RecipeSearchController(bean);
        List<RecipeReturnBean> ricettereturnbean=controller.trovaricette();

        if(ricettereturnbean!=null){
            mostraricette(ricettereturnbean);
        }else{
           System.out.println("ricettereturnbean is null");
        }
    }



    @FXML
    public void initialize() throws IOException {
        // Associare le azioni ai MenuItem (cambiano il testo del bottone)
        vegan.setOnAction(e -> tipoDieta.setText("Vegan"));
        vegetarian.setOnAction(e -> tipoDieta.setText("Vegetarian"));
        omnivorous.setOnAction(e -> tipoDieta.setText("Onnivorous"));

        breakfast.setOnAction(e -> tipoPasto.setText("Breakfast"));
        launch.setOnAction(e -> tipoPasto.setText("Lunch"));
        dinner.setOnAction(e -> tipoPasto.setText("Dinner"));
    }

    public void mostraricette(List<RecipeReturnBean> listaRicette) {

        listaRicetteview.getItems().clear();
        for (RecipeReturnBean ricetta : listaRicette) {
            String nomeRicetta = ricetta.getRecipeName();  // Prendi il nome
            String tipoDieta = ricetta.getTypeofDiet();    // Prendi il tipo di dieta
            String tipoPasto = ricetta.getTypeofMeal();// Prendi il tipo di pasto
            String numingredienti = ricetta.getNumIngredients();
            String ingredienti = ricetta.getIngredients();
            String descrizione = ricetta.getDescription();
            String author = ricetta.getAuthor();
            String riga = nomeRicetta + " - " + tipoDieta + " - " + tipoPasto + " - " + numingredienti + " - " + ingredienti + " - " + descrizione + " - " + author;  // Stringa da mostrare
            listaRicetteview.getItems().add(riga);
        }
    }
}
