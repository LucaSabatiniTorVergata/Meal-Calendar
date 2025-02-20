package com.example.mealcalendar;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GraphicController {

    private static final Logger LOGGER = Logger.getLogger(GraphicController.class.getName());


    private GraphicController() {
    }

    public static void cambiascena(Stage stage, String fxmlview) {
        try {
            FXMLLoader loader = new FXMLLoader(GraphicController.class.getResource(fxmlview));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Errore durante il caricamento della scena: " + fxmlview, e);

        }
    }
}
