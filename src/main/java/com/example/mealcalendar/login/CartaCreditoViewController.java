package com.example.mealcalendar.login;


import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class CartaCreditoViewController {

    @FXML
    private void goBack(ActionEvent event) throws IOException {
        Parent nuovaSchermata = FXMLLoader.load(getClass().getResource("checkout-view.fxml"));
        Scene nuovaScena=new Scene(nuovaSchermata);
        Stage finestra =(Stage)((Node)event.getSource()).getScene().getWindow();
        finestra.setScene(nuovaScena);
        finestra.show();
    }
    @FXML
    private void goBackHome(ActionEvent event) throws IOException {
        Parent nuovaSchermata = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene nuovaScena=new Scene(nuovaSchermata);
        Stage finestra=(Stage)((Node)event.getSource()).getScene().getWindow();
        finestra.setScene(nuovaScena);
        finestra.show();
    }
    @FXML
    private void payWithCard(ActionEvent event) throws IOException {

        Parent nuovaSchermata = FXMLLoader.load(getClass().getResource("login-view.fxml"));
        Scene nuovaScena=new Scene(nuovaSchermata);
        Stage finestra=(Stage)((Node)event.getSource()).getScene().getWindow();
        finestra.setScene(nuovaScena);
        finestra.show();
    }

}
