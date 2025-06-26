package com.example.mealcalendar.login;


import java.awt.*;
import java.io.IOException;

import com.example.mealcalendar.GraphicController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class CartaCreditoViewController {

    @FXML
    private Button indietro;

    @FXML
    private Button home;

    @FXML
    private Button pay;

    @FXML
    private void goBack(ActionEvent event) throws IOException {
        Stage stage = (Stage) indietro.getScene().getWindow();
        GraphicController.cambiascena(stage, "checkout-view.fxml");
    }
    @FXML
    private void goBackHome(ActionEvent event) throws IOException {
        Stage stage = (Stage) home.getScene().getWindow();
        GraphicController.cambiascena(stage, "register-view.fxml");

    }
    @FXML
    private void payWithCard(ActionEvent event) throws IOException {
        Stage stage = (Stage) pay.getScene().getWindow();
        GraphicController.cambiascena(stage, "login-view.fxml");
    }

}
