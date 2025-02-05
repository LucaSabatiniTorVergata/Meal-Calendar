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
import javafx.scene.control.Button;

public class RecipeAddViewBoundary {

    @FXML
    private Button backview;

    @FXML
    private Button homeview;



    @FXML
    private void backview(ActionEvent event) throws IOException {

        Stage stage = (Stage) backview.getScene().getWindow();
        GraphicController.cambiascena(stage, "recipe-view.fxml");
    }

    @FXML
    private void homeview(ActionEvent event) throws IOException {

        Stage stage = (Stage) homeview.getScene().getWindow();
        GraphicController.cambiascena(stage, "usermenu-view.fxml");
    }
}
