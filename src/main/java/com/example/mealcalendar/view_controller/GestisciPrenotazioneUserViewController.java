package com.example.mealcalendar.view_controller;

import com.example.mealcalendar.GraphicController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class GestisciPrenotazioneUserViewController {

    @FXML
    private Button deleteBooking;

    @FXML
    private Button homebutton;

    @FXML
    private Label lableemail;

    @FXML
    private ProgressIndicator loadingIndicator;

    @FXML
    private ListView<?> prenotazioniListView;

    @FXML
    private Label welcomelabel;

    @FXML
    void handleclick(MouseEvent event) {

    }

    @FXML
    void loadHomePageUser(ActionEvent event) {

        Stage stage = (Stage) homebutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "menu-view.fxml");
    }

}
