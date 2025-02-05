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


public class RecipeEdit2ViewController {

    @FXML
    private Button homereturn;
    @FXML
    private Button returntoedit;

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
}
