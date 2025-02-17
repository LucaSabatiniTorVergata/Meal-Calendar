package com.example.mealcalendar;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class GraphicController {

    private GraphicController(){

    }

    public static void cambiascena(Stage stage, String fxmlview) {

        try {
            FXMLLoader loader = new FXMLLoader(GraphicController.class.getResource(fxmlview));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            System.out.println("sborra");
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
