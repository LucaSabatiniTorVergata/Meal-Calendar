package com.example.mealcalendar;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;


public class HelloController{

    @FXML
    private Button loginbutton;

    @FXML
    private TextField passInChiaro;

    @FXML
    private PasswordField passWordBox;

    @FXML
    void LoadLoginPage(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("register-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280,720);

        Stage stage = (Stage) fxmlLoader.getRoot().getScene().getWindow();
        stage.setScene(scene);

    }

}
