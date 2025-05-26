package com.example.mealcalendar.login;

import java.io.IOException;

import com.example.mealcalendar.GraphicController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class CheckoutViewController {

    @FXML
    private Button carta;

    @FXML
    private Button indietro;

    @FXML
    void goBack(ActionEvent event)throws IOException {
        Stage stage = (Stage) indietro.getScene().getWindow();
        GraphicController.cambiascena(stage, "hello-view.fxml");
    }

    @FXML
    void payWithCard(ActionEvent event)throws IOException {
        Stage stage = (Stage) carta.getScene().getWindow();
        GraphicController.cambiascena(stage, "cartacredito-view.fxml");
    }




}
