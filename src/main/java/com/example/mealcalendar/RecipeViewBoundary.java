package com.example.mealcalendar;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

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
    private ListView<ReturnRecipesBean> listaRicetteview;

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
    private void homeview(ActionEvent event) throws IOException {

        Stage stage = (Stage) returnhome.getScene().getWindow();
        GraphicController.cambiascena(stage, "usermenu-view.fxml");
    }

    @FXML
    private void searchrecipies(ActionEvent event) throws IOException {

        if(tipoDieta.getText().equalsIgnoreCase("Onnivorous")){
            tipoDietaSelezionato = "";

        }
        else {
            tipoDietaSelezionato = tipoDieta.getText();
        }

        pastoSelezionato=tipoPasto.getText();

        RecipeFiltersBean fitri= new RecipeFiltersBean(tipoDietaSelezionato,pastoSelezionato);
        //RecipeSearchController controller= new RecipeSearchController(fitri);

        //List<ReturnRecipesBean> recipesBeans=controller.trovaRicette();

        //mostraRicette(recipesBeans);
    }



    @FXML
    public void initialize() throws IOException {
        // Associare le azioni ai MenuItem (cambiano il testo del bottone)
        vegan.setOnAction(e -> tipoDieta.setText("Vega"));
        vegetarian.setOnAction(e -> tipoDieta.setText("Vegetarian"));
        omnivorous.setOnAction(e -> tipoDieta.setText("Onnivorous"));

        breakfast.setOnAction(e -> tipoPasto.setText("Breakfast"));
        launch.setOnAction(e -> tipoPasto.setText("Lunch"));
        dinner.setOnAction(e -> tipoPasto.setText("Dinner"));

        RecipeFiltersBean filtro = new RecipeFiltersBean(tipoDietaSelezionato, pastoSelezionato);


    }



}
