package com.example.mealcalendar;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class RecipeEditViewController {

    @FXML
    private Button recipeEdit2;

    @FXML
    private Button ritorno;


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
}
