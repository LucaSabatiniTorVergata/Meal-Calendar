package com.example.mealcalendar.view_controller;

import com.example.mealcalendar.GraphicController;
import com.example.mealcalendar.bean.RistoranteBean;
import com.example.mealcalendar.controller_applicativo.PrenotazioneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class PrenotazioneRistoranteViewController {

    private final PrenotazioneController prenotazioneController = new PrenotazioneController();

    @FXML private Button backhome;
    @FXML private DatePicker calendar;
    @FXML private Button confirmButton;
    @FXML private Button ricarica;
    @FXML private SplitMenuButton tipoSelezionato;
    @FXML private ListView<RistoranteBean> listaRistoranti;
    @FXML private TextField orascelta;

    @FXML
    void confermaPrenotazione(ActionEvent event) { }

    @FXML
    void homeview(ActionEvent event) {
        Stage stage = (Stage) backhome.getScene().getWindow();
        GraphicController.cambiascena(stage, "menu-view.fxml");
    }

    @FXML
    void caricaRistoranti(ActionEvent event) {
        listaRistoranti.getItems().clear();
        listaRistoranti.getItems().addAll(prenotazioneController.getRistoranti());

        listaRistoranti.setCellFactory(lv -> new ListCell<RistoranteBean>() {
            @Override
            protected void updateItem(RistoranteBean item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getUsername()); // mostra solo username
            }
        });
    }
}
