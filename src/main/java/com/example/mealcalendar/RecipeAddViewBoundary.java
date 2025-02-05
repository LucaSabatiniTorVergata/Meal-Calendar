package com.example.mealcalendar;

import java.io.IOException;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import org.w3c.dom.Text;

public class RecipeAddViewBoundary {


    @FXML
    private void backview(ActionEvent event) throws IOException {
        Parent nuovaSchermata = FXMLLoader.load(getClass().getResource("recipe-view.fxml"));
        Scene nuovaScena = new Scene(nuovaSchermata);
        Stage finestra = (Stage) ((Node) event.getSource()).getScene().getWindow();
        finestra.setScene(nuovaScena);
        finestra.show();
    }

    @FXML
    private void homeview(ActionEvent event) throws IOException {
        Parent nuovaSchermata = FXMLLoader.load(getClass().getResource("usermenu-view.fxml"));
        Scene nuovaScena = new Scene(nuovaSchermata);
        Stage finestra = (Stage) ((Node) event.getSource()).getScene().getWindow();
        finestra.setScene(nuovaScena);
        finestra.show();
    }
}
