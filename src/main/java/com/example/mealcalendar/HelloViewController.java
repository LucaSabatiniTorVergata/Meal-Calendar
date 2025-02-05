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

public class HelloViewController {

    @FXML
    private Button quibutton;
    @FXML
    private Button register;
    @FXML
    private Button login;


    @FXML
    private void guestmenuview(ActionEvent event) throws IOException  {

        Stage stage = (Stage) quibutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "guestmenu-view.fxml");
    }

    @FXML
    private void loginview(ActionEvent event) throws IOException {

        Stage stage = (Stage) login.getScene().getWindow();
        GraphicController.cambiascena(stage, "login-view.fxml");
    }

    @FXML
    private void register(ActionEvent event) throws IOException {

        Stage stage = (Stage) register.getScene().getWindow();
        GraphicController.cambiascena(stage, "checkout-view.fxml");
    }

}





