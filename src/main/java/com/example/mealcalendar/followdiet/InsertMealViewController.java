package com.example.mealcalendar.followdiet;

import com.example.mealcalendar.GraphicController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class InsertMealViewController {

    @FXML
    private Button home;

    @FXML
    public void backview() {
        Stage stage = (Stage) home.getScene().getWindow();
        GraphicController.cambiascena(stage, "usermenu-view.fxml");

    }

}
