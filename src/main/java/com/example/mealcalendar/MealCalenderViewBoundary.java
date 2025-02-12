package com.example.mealcalendar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MealCalenderViewBoundary {

    private static final Logger LOGGER = Logger.getLogger(MealCalenderViewBoundary.class.getName());

    @FXML
    private Button backhome;

    @FXML
    private SplitMenuButton posizione;

    @FXML
    private DatePicker calendar;

    @FXML
    private Button confirmButton;

    @FXML
    private MenuItem home;

    @FXML
    private MenuItem restaurant;

    @FXML
    private Label ristoranteSelezionato;

    public static boolean sceltaLuogo = false;
    public static boolean vengoDaCalendar = false;
    public static String nomePerCalendar = "";

    @FXML
    private void initialize() {
        home.setOnAction(e -> {
            posizione.setText("casa");
            sceltaLuogo = false;
        });

        restaurant.setOnAction(e -> {
            posizione.setText("ristorante");
            sceltaLuogo = true;
        });
        if (!nomePerCalendar.isEmpty()) {
            ristoranteSelezionato.setText("Ristorante selezionato: " + nomePerCalendar);
        }

        LOGGER.log(Level.INFO, "Scelta luogo: {0}", sceltaLuogo);
    }

    @FXML
    private void confirmChoise(ActionEvent actionEvent) {
        if (sceltaLuogo) {
            vengoDaCalendar = true;
            Stage stage = (Stage) confirmButton.getScene().getWindow();
            GraphicController.cambiascena(stage, "findrestaurantuser-view.fxml");
        } else {
            vengoDaCalendar = true;
            Stage stage = (Stage) confirmButton.getScene().getWindow();
            GraphicController.cambiascena(stage, "recipe-view.fxml");
        }
        LOGGER.log(Level.INFO, "sono l'altra pagina: {0}", nomePerCalendar);
    }

    @FXML
    private void homeview(ActionEvent event) {
        Stage stage = (Stage) backhome.getScene().getWindow();
        GraphicController.cambiascena(stage, "usermenu-view.fxml");
    }
}
