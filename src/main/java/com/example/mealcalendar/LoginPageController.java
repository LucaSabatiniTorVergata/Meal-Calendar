package com.example.mealcalendar;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


public class LoginPageController {

        @FXML
        private void indietroview(ActionEvent event) throws IOException {
            Parent nuovaSchermata = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            Scene nuovaScena=new Scene(nuovaSchermata);
            Stage finestra =(Stage)((Node)event.getSource()).getScene().getWindow();
            finestra.setScene(nuovaScena);
            finestra.show();

        }
        @FXML
        private void logIn(ActionEvent event) throws IOException {
            Parent nuovaSchermata = FXMLLoader.load(getClass().getResource("usermenu-view.fxml"));
            Scene nuovaScena=new Scene(nuovaSchermata);
            Stage finestra=(Stage)((Node)event.getSource()).getScene().getWindow();
            finestra.setScene(nuovaScena);
            finestra.show();
        }
        @FXML
        private void here(ActionEvent event) throws IOException {
            Parent nuovaSchermata = FXMLLoader.load(getClass().getResource("guestmenu-view.fxml"));
            Scene nuovaScena=new Scene(nuovaSchermata);
            Stage finestra =(Stage)((Node)event.getSource()).getScene().getWindow();
            finestra.setScene(nuovaScena);
            finestra.show();


        }
    }
