package com.example.mealcalendar;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
            e.printStackTrace(); // stampa l'eccezione
            LOGGER.log(Level.SEVERE, "Errore durante il caricamento della scena: " + fxmlview, e);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Impossibile caricare la scena");
            alert.setContentText("Si è verificato un errore durante il caricamento della schermata: " + fxmlview
                    + "\nDettagli: " + e.getMessage());
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace(); // stampa l'eccezione imprevista
            LOGGER.log(Level.SEVERE, "Errore imprevisto durante il cambio scena: " + fxmlview, e);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Errore imprevisto");
            alert.setContentText("Si è verificato un errore durante il cambio schermata.\nDettagli: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
