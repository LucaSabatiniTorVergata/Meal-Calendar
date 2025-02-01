package com.example.mealcalendar;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


public class MainMenuController {



    //metodi eseguibi dal guest
    @FXML
    private void findRestaurantGuest(ActionEvent event) throws IOException {
        Parent nuovaSchermata = FXMLLoader.load(getClass().getResource("findrestaurantguest-view.fxml"));
        Scene nuovaScena=new Scene(nuovaSchermata);
        Stage finestra =(Stage)((Node)event.getSource()).getScene().getWindow();
        finestra.setScene(nuovaScena);
        finestra.show();
    }

    @FXML
    private void goHome(ActionEvent event) throws IOException {
        Parent nuovaSchermata = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene nuovaScena=new Scene(nuovaSchermata);
        Stage finestra =(Stage)((Node)event.getSource()).getScene().getWindow();
        finestra.setScene(nuovaScena);
        finestra.show();
    }
    //metodi eseguibili dall'user
    @FXML
    private void findRestaurantUser(ActionEvent event) throws IOException {
        Parent nuovaSchermata = FXMLLoader.load(getClass().getResource("findrestaurantuser-view.fxml"));
        Scene nuovaScena=new Scene(nuovaSchermata);
        Stage finestra =(Stage)((Node)event.getSource()).getScene().getWindow();
        finestra.setScene(nuovaScena);
        finestra.show();
    }

    @FXML
    private void loadCalendarMenu(ActionEvent event) throws IOException {
        Parent nuovaSchermata = FXMLLoader.load(getClass().getResource("mealcalendar-view.fxml"));
        Scene nuovaScena=new Scene(nuovaSchermata);
        Stage finestra =(Stage)((Node)event.getSource()).getScene().getWindow();
        finestra.setScene(nuovaScena);
        finestra.show();
    }

    @FXML
    private void loadFindRecpe(ActionEvent event) throws IOException {
        Parent nuovaSchermata = FXMLLoader.load(getClass().getResource("recipe-view.fxml"));
        Scene nuovaScena=new Scene(nuovaSchermata);
        Stage finestra =(Stage)((Node)event.getSource()).getScene().getWindow();
        finestra.setScene(nuovaScena);
        finestra.show();
    }

    @FXML
    private void loadFrigeMenu(ActionEvent event) throws IOException {
        Parent nuovaSchermata = FXMLLoader.load(getClass().getResource("fridge-view.fxml"));
        Scene nuovaScena=new Scene(nuovaSchermata);
        Stage finestra =(Stage)((Node)event.getSource()).getScene().getWindow();
        finestra.setScene(nuovaScena);
        finestra.show();
    }

    @FXML
    private void goBack(ActionEvent event) throws IOException {
        Parent nuovaSchermata = FXMLLoader.load(getClass().getResource("login-view.fxml"));
        Scene nuovaScena=new Scene(nuovaSchermata);
        Stage finestra =(Stage)((Node)event.getSource()).getScene().getWindow();
        finestra.setScene(nuovaScena);
        finestra.show();
    }


}
