package com.example.mealcalendar;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;


public class RecipeEdit2ViewController {

    @FXML
    private Button homereturn;
    @FXML
    private Button returntoedit;
    @FXML
    private Button apply;
    @FXML
    private TextField nomericetta;
    @FXML
    private SplitMenuButton tipodieta;
    @FXML
    private SplitMenuButton tipopasto;
    @FXML
    private TextField descrizione;
    @FXML
    private TextField numeroing;
    @FXML
    private TextField ingredienti;


    @FXML
    private MenuItem vegan;
    @FXML
    private MenuItem vegetariana;
    @FXML
    private MenuItem omnivora;

    @FXML
    private MenuItem colazione;
    @FXML
    private MenuItem pranzo;
    @FXML
    private MenuItem cena;

    private String ricettascelta;

    @FXML
    private void homeview(ActionEvent event) throws IOException {

        Stage stage = (Stage) homereturn.getScene().getWindow();
        GraphicController.cambiascena(stage, "usermenu-view.fxml");
    }

    @FXML
    private void recipeeditview(ActionEvent event) throws IOException {

        Stage stage = (Stage) returntoedit.getScene().getWindow();
        GraphicController.cambiascena(stage, "recipeedit-view.fxml");
    }

    public void setRecipe(String recipe) throws IOException {
       ricettascelta=recipe;
    }

    @FXML
    private void initialize() throws IOException {

        String firstPart = ricettascelta.split(" - ")[0];
        nomericetta.setText(firstPart);


        vegan.setOnAction(e -> tipodieta.setText("Vegano"));
        vegetariana.setOnAction(e -> tipodieta.setText("Vegetariano"));
        omnivora.setOnAction(e -> tipodieta.setText("Onnivoro"));

        colazione.setOnAction(e -> tipopasto.setText("Colazione"));
        pranzo.setOnAction(e -> tipopasto.setText("Pranzo"));
        cena.setOnAction(e -> tipopasto.setText("Cena"));



        String fourthPart = ricettascelta.split(" - ")[3];
        numeroing.setText(fourthPart);

        String fifthPart = ricettascelta.split(" - ")[4];
        ingredienti.setText(fifthPart);

        String sixthPart = ricettascelta.split(" - ")[5];
        descrizione.setText(sixthPart);




    }

}
