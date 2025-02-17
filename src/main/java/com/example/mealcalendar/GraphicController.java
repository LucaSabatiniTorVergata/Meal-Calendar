package com.example.mealcalendar;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GraphicController {

    private GraphicController() {
    }

    public static void cambiascena(Stage stage, String fxmlview, boolean useDB, boolean useDemo) {
        try {
            FXMLLoader loader = new FXMLLoader(GraphicController.class.getResource(fxmlview));
            Parent root = loader.load();

            // Recupera il controller della nuova scena
            Object controller = loader.getController();

            // Se il controller ha i metodi setUseDB e setUseDemo, imposta i valori
            if (controller instanceof PassaParametri) {
                ((PassaParametri) controller).setUseDB(useDB);
                ((PassaParametri) controller).setUseDemo(useDemo);
            }

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            System.out.println("sborrar");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
