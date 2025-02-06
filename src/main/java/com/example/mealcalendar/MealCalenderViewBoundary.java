package com.example.mealcalendar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class MealCalenderViewBoundary {

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


    public static boolean sceltaLuogo = false;// sempre falso perchè di base si mangia a casa
    public static boolean vengoDaCalendar = false; //mi serve per dire a restaurant di non aprire browser

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

        System.out.println(sceltaLuogo);
    }

    @FXML
    private void confirmChoise(ActionEvent actionEvent) {
        if(sceltaLuogo) {
            Stage stage = (Stage) confirmButton.getScene().getWindow();
            GraphicController.cambiascena(stage, "findrestaurantuser-view.fxml");
            vengoDaCalendar = true;
        }
        else {
            Stage stage = (Stage) confirmButton.getScene().getWindow();
            GraphicController.cambiascena(stage, "recipe-view.fxml");
            vengoDaCalendar = true;
        }

    }

    @FXML
    private void homeview(ActionEvent event) {
        Stage stage = (Stage) backhome.getScene().getWindow();
        GraphicController.cambiascena(stage, "usermenu-view.fxml");
    }

}

