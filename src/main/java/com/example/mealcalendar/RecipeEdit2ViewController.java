package com.example.mealcalendar;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


public class RecipeEdit2ViewController {


    @FXML
    private void homeview(ActionEvent event) throws IOException {
        Parent nuovaSchermata = FXMLLoader.load(getClass().getResource("usermenu-view.fxml"));
        Scene nuovaScena = new Scene(nuovaSchermata);
        Stage finestra = (Stage) ((Node) event.getSource()).getScene().getWindow();
        finestra.setScene(nuovaScena);
        finestra.show();
    }

    @FXML
    private void recipeeditview(ActionEvent event) throws IOException {
        Parent nuovaSchermata = FXMLLoader.load(getClass().getResource("recipeedit-view.fxml"));
        Scene nuovaScena = new Scene(nuovaSchermata);
        Stage finestra = (Stage) ((Node) event.getSource()).getScene().getWindow();
        finestra.setScene(nuovaScena);
        finestra.show();
    }
}
