package com.example.mealcalendar.view_controller;

import com.example.mealcalendar.GraphicController;
import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.bean.RistoranteBean;
import com.example.mealcalendar.controller_applicativo.PrenotazioneController;
import com.example.mealcalendar.model.TipologiaRistorante;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class PrenotazioneRistoranteViewController {

    private final PrenotazioneController prenotazioneController = new PrenotazioneController();

    @FXML private Button backhome;
    @FXML private DatePicker calendar;
    @FXML private Button confirmButton;
    @FXML private Button ricarica;
    @FXML private SplitMenuButton tipoSelezionato;
    @FXML private ListView<RistoranteBean> listaRistoranti;
    @FXML private TextField orascelta;

    private TipologiaRistorante tipologiaSelezionata; // memorizza scelta filtro

    @FXML
    public void initialize() {
        // Popola lo SplitMenuButton con le tipologie dell'enum
        tipoSelezionato.getItems().clear();
        for (TipologiaRistorante t : TipologiaRistorante.values()) {
            MenuItem item = new MenuItem(t.name());
            item.setOnAction(e -> {
                tipoSelezionato.setText(t.name());
                tipologiaSelezionata = t;
            });
            tipoSelezionato.getItems().add(item);
        }
    }

    @FXML
    void confermaPrenotazione(ActionEvent event) {
        if (calendar.getValue() == null || orascelta.getText().isEmpty()) {
            showAlert("Errore", "Compila data e ora prima di confermare!");
            return;
        }

        RistoranteBean ristoranteScelto = listaRistoranti.getSelectionModel().getSelectedItem();
        if (ristoranteScelto == null) {
            showAlert("Errore", "Seleziona un ristorante dalla lista!");
            return;
        }

        // Prendi lo username dell'utente loggato
        String usernameUtente = SessionManagerSLT.getInstance().getLoggedInUsername();

        // Nome ristorante
        String nomeRistorante = ristoranteScelto.getNome();

        // Debug
        System.out.println("Prenotazione confermata:");
        System.out.println("Utente: " + usernameUtente);
        System.out.println("Ristorante: " + nomeRistorante);
        System.out.println("Data: " + calendar.getValue());
        System.out.println("Ora: " + orascelta.getText());
        System.out.println("Tipologia: " + (tipologiaSelezionata != null ? tipologiaSelezionata.name() : "Nessuna"));

        // Salva la prenotazione nel controller
        prenotazioneController.salvaPrenotazione(usernameUtente, nomeRistorante, calendar.getValue().toString(), orascelta.getText());

        showAlert("Successo", "Prenotazione confermata per " + nomeRistorante + " da parte di " + usernameUtente);
    }

    @FXML
    void homeview(ActionEvent event) {
        Stage stage = (Stage) backhome.getScene().getWindow();
        GraphicController.cambiascena(stage, "menu-view.fxml");
    }

    @FXML
    void caricaRistoranti(ActionEvent event) {
        listaRistoranti.getItems().clear();
        List<RistoranteBean> ristoranti = prenotazioneController.getRistoranti();

        // Applica filtro se selezionata tipologia
        if (tipologiaSelezionata != null) {
            ristoranti = ristoranti.stream()
                    .filter(r -> r.getTipologiaRistorante() == tipologiaSelezionata)
                    .toList();
        }

        listaRistoranti.getItems().addAll(ristoranti);

        // Mostra nome + indirizzo nella ListView
        listaRistoranti.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(RistoranteBean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("");
                } else {
                    setText(item.getNome() + " - " + item.getIndirizzo());
                }
            }
        });
    }

    // Metodo di utilità per mostrare alert
    private void showAlert(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}
