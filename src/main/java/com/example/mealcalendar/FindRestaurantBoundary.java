package com.example.mealcalendar;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitMenuButton;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class FindRestaurantBoundary {
    @FXML private SplitMenuButton ;

    @FXML
    private void LoadHomePageGuest(ActionEvent event) throws IOException {
        Parent NuovaSchermata = FXMLLoader.load(getClass().getResource("guestmenu-view.fxml"));
        Scene NuovaScena=new Scene(NuovaSchermata);
        Stage Finestra=(Stage)((Node)event.getSource()).getScene().getWindow();
        Finestra.setScene(NuovaScena);
        Finestra.show();

    }
    @FXML
    private void RegisterGuest(ActionEvent event) throws IOException {
        Parent NuovaSchermata = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene NuovaScena=new Scene(NuovaSchermata);
        Stage Finestra=(Stage)((Node)event.getSource()).getScene().getWindow();
        Finestra.setScene(NuovaScena);
        Finestra.show();

    }
    @FXML
    private void LoadHomePageUser(ActionEvent event) throws IOException {
        Parent NuovaSchermata = FXMLLoader.load(getClass().getResource("usermenu-view.fxml"));
        Scene NuovaScena = new Scene(NuovaSchermata);
        Stage Finestra = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Finestra.setScene(NuovaScena);
        Finestra.show();
    }

}
