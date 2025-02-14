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


    private String nomeRicetta;
    private String numIngredienti;
    private String ingredientiRicetta;
    private String descrizioneRicetta;
    private String dieta;
    private String pasto;



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

        this.ricettascelta = recipe;
        updateUI();

    }

    public void updateUI() {
        if (ricettascelta != null) {
            String[] parts = ricettascelta.split(" - ");
            if (parts.length >= 6) {
                nomericetta.setText(parts[0]);
                tipodieta.setText(parts[1]);
                tipopasto.setText(parts[2]);
                numeroing.setText(parts[3]);
                ingredienti.setText(parts[4]);
                descrizione.setText(parts[5]);
            }
        } else {
            System.out.println("Errore: ricettascelta è null!");
        }

        vegan.setOnAction(e -> tipodieta.setText("Vegano"));
        vegetariana.setOnAction(e -> tipodieta.setText("Vegetariano"));
        omnivora.setOnAction(e -> tipodieta.setText("Onnivoro"));

        colazione.setOnAction(e -> tipopasto.setText("Colazione"));
        pranzo.setOnAction(e -> tipopasto.setText("Pranzo"));
        cena.setOnAction(e -> tipopasto.setText("Cena"));
    }


    @FXML
    private void applyview(ActionEvent event) throws IOException {

        nomeRicetta = nomericetta.getText();
        numIngredienti = numeroing.getText();
        ingredientiRicetta = ingredienti.getText();
        descrizioneRicetta = descrizione.getText();
        pasto=tipopasto.getText();
        dieta=tipodieta.getText();


        RecipeEdit2Bean bean=new RecipeEdit2Bean(ricettascelta,nomeRicetta,dieta,pasto,numIngredienti,ingredientiRicetta,descrizioneRicetta,SessionManagerSLT.getInstance().getLoggedInUsername());
        RecipeEdit2Controller controller = new RecipeEdit2Controller(bean);
        controller.cambiaRicetta();


        Stage stage = (Stage) homereturn.getScene().getWindow();
        GraphicController.cambiascena(stage, "recipe-view.fxml");


    }

}
