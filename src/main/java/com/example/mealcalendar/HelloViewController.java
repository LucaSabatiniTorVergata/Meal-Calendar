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
    private void guestmenuview(ActionEvent event) throws IOException  {

        Stage stage = (Stage) quibutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "guestmenu-view.fxml");
    }

    @FXML
    private void loginview(ActionEvent event) throws IOException {
        Parent nuovaSchermata = FXMLLoader.load(getClass().getResource("login-view.fxml"));
        Scene nuovaScena=new Scene(nuovaSchermata);
        Stage finestra=(Stage)((Node)event.getSource()).getScene().getWindow();
        finestra.setScene(nuovaScena);
        finestra.show();
    }

    @FXML
    private void register(ActionEvent event) throws IOException {
        Parent nuovaSchermata = FXMLLoader.load(getClass().getResource("checkout-view.fxml"));
        Scene nuovaScena=new Scene(nuovaSchermata);
        Stage finestra=(Stage)((Node)event.getSource()).getScene().getWindow();
        finestra.setScene(nuovaScena);
       finestra.show();
    }

}





