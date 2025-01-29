package com.example.mealcalendar;


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
        Parent NuovaSchermata = FXMLLoader.load(getClass().getResource("checkout-view.fxml"));
        Scene NuovaScena=new Scene(NuovaSchermata);
        Stage Finestra=(Stage)((Node)event.getSource()).getScene().getWindow();
        Finestra.setScene(NuovaScena);
        Finestra.show();
    }
    @FXML
    private void goBackHome(ActionEvent event) throws IOException {
        Parent NuovaSchermata = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene NuovaScena=new Scene(NuovaSchermata);
        Stage Finestra=(Stage)((Node)event.getSource()).getScene().getWindow();
        Finestra.setScene(NuovaScena);
        Finestra.show();
    }
    @FXML
    private void payWithCard(ActionEvent event) throws IOException {

        Parent NuovaSchermata = FXMLLoader.load(getClass().getResource("login-view.fxml"));
        Scene NuovaScena=new Scene(NuovaSchermata);
        Stage Finestra=(Stage)((Node)event.getSource()).getScene().getWindow();
        Finestra.setScene(NuovaScena);
        Finestra.show();
    }

}
