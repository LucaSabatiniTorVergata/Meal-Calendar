package com.example.mealcalendar;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;


public class RecipeEditViewController {

    @FXML
    private Button recipeEdit2;

    @FXML
    private Button ritorno;

    @FXML
    private ListView ricetteview;


    @FXML
    private void recipeedit2view(ActionEvent event) throws IOException {

        Stage stage = (Stage) recipeEdit2.getScene().getWindow();
        GraphicController.cambiascena(stage, "recipeedit2-view.fxml");
    }
    @FXML
    private void backview(ActionEvent event) throws IOException {
       
        Stage stage = (Stage) ritorno.getScene().getWindow();
        GraphicController.cambiascena(stage, "recipe-view.fxml");
    }

    @FXML
    public void initialize() {

        ricetteview.getItems().clear();
        String user=SessionManagerSLT.getInstance().getLoggedInUsername();
        RecipeEditBean bean=new RecipeEditBean(user);
        RecipeEditController controller=new RecipeEditController(bean);
        List<RecipeReturnBean>returnricette=controller.mostraricette();
        for (RecipeReturnBean ricetta : returnricette) {

            String nomeRicetta = ricetta.getRecipeName();  // Prendi il nome


            String tipodieta = ricetta.getTypeofDiet();    // Prendi il tipo di dieta


            String tipopasto = ricetta.getTypeofMeal(); // Prendi il tipo di pasto


            String numingredienti = ricetta.getNumIngredients();


            String ingredienti = ricetta.getIngredients();


            String descrizione = ricetta.getDescription();


            String author = ricetta.getAuthor();


            String riga = nomeRicetta + " - " + tipodieta + " - " + tipopasto
                    + " - " + numingredienti + " - " + ingredienti + " - " + descrizione + " - " + author;  // Stringa da mostrare


            ricetteview.getItems().add(riga);


        }
    }


}
