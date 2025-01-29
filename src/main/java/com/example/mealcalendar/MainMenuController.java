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
    private void FindRestaurantGuest(ActionEvent event) throws IOException {
        Parent NuovaSchermata = FXMLLoader.load(getClass().getResource("findrestaurantguest-view.fxml"));
        Scene NuovaScena=new Scene(NuovaSchermata);
        Stage Finestra=(Stage)((Node)event.getSource()).getScene().getWindow();
        Finestra.setScene(NuovaScena);
        Finestra.show();
    }

    @FXML
    private void goHome(ActionEvent event) throws IOException {
        Parent NuovaSchermata = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene NuovaScena=new Scene(NuovaSchermata);
        Stage Finestra=(Stage)((Node)event.getSource()).getScene().getWindow();
        Finestra.setScene(NuovaScena);
        Finestra.show();
    }
    //metodi eseguibili dall'user
    @FXML
    private void FindRestaurantUser(ActionEvent event) throws IOException {
        Parent NuovaSchermata = FXMLLoader.load(getClass().getResource("findrestaurantuser-view.fxml"));
        Scene NuovaScena=new Scene(NuovaSchermata);
        Stage Finestra=(Stage)((Node)event.getSource()).getScene().getWindow();
        Finestra.setScene(NuovaScena);
        Finestra.show();
    }

    @FXML
    private void LoadCalendarMenu(ActionEvent event) throws IOException {
        Parent NuovaSchermata = FXMLLoader.load(getClass().getResource("mealcalendar-view.fxml"));
        Scene NuovaScena=new Scene(NuovaSchermata);
        Stage Finestra=(Stage)((Node)event.getSource()).getScene().getWindow();
        Finestra.setScene(NuovaScena);
        Finestra.show();
    }

    @FXML
    private void LoadFindRecpe(ActionEvent event) throws IOException {
        Parent NuovaSchermata = FXMLLoader.load(getClass().getResource("recipe-view.fxml"));
        Scene NuovaScena=new Scene(NuovaSchermata);
        Stage Finestra=(Stage)((Node)event.getSource()).getScene().getWindow();
        Finestra.setScene(NuovaScena);
        Finestra.show();
    }

    @FXML
    private void LoadFrigeMenu(ActionEvent event) throws IOException {
        Parent NuovaSchermata = FXMLLoader.load(getClass().getResource("fridge-view.fxml"));
        Scene NuovaScena=new Scene(NuovaSchermata);
        Stage Finestra=(Stage)((Node)event.getSource()).getScene().getWindow();
        Finestra.setScene(NuovaScena);
        Finestra.show();
    }

    @FXML
    private void goBack(ActionEvent event) throws IOException {
        Parent NuovaSchermata = FXMLLoader.load(getClass().getResource("login-view.fxml"));
        Scene NuovaScena=new Scene(NuovaSchermata);
        Stage Finestra=(Stage)((Node)event.getSource()).getScene().getWindow();
        Finestra.setScene(NuovaScena);
        Finestra.show();
    }


}
