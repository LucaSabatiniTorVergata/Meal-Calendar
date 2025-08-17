package com.example.mealcalendar.view_controller;
import com.example.mealcalendar.GraphicController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class PrenotazioneRistoranteViewController {
    @FXML
    private Button backhome;

    @FXML
    private DatePicker calendar;

    @FXML
    private Button confirmButton;

    @FXML
    private ListView<?> listaRistoranti;

    @FXML
    private TextField orascelta;

    @FXML
    void confermaPrenotazione(ActionEvent event) {

    }

    @FXML
    void homeview(ActionEvent event) {


        Stage stage = (Stage) backhome.getScene().getWindow();
        GraphicController.cambiascena(stage, "menu-view.fxml");
    }
}
