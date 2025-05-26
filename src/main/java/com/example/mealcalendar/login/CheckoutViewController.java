package com.example.mealcalendar.login;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class CheckoutViewController {

    @FXML
    void goBack(ActionEvent event)throws IOException {
        Parent nuovaSchermata = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene nuovaScena=new Scene(nuovaSchermata);
        Stage finestra=(Stage)((Node)event.getSource()).getScene().getWindow();
        finestra.setScene(nuovaScena);
        finestra.show();
    }

    @FXML
    void payWithCard(ActionEvent event)throws IOException {
        Parent nuovaSchermata = FXMLLoader.load(getClass().getResource("cartacredito-view.fxml"));
        Scene nuovaScena=new Scene(nuovaSchermata);
        Stage finestra=(Stage)((Node)event.getSource()).getScene().getWindow();
        finestra.setScene(nuovaScena);
        finestra.show();
    }




}
