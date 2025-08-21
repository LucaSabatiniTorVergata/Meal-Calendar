package com.example.mealcalendar.view_controller;

import com.example.mealcalendar.GraphicController;
import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.bean.PrenotazioneBean;
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
    @FXML private TextField orascelta;      // Ora prenotazione
    @FXML private TextField postiASedere;    // Numero posti

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

        // Impedisci selezione date passate
        calendar.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(java.time.LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isBefore(java.time.LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;"); // opzionale: colore rosato per le date disabilitate
                }
            }
        });
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

        String usernameUtente = SessionManagerSLT.getInstance().getLoggedInUsername();

        // Lettura posti a sedere
        int posti = 1; // default
        if (postiASedere != null && !postiASedere.getText().isEmpty()) {
            try {
                posti = Integer.parseInt(postiASedere.getText());
            } catch (NumberFormatException e) {
                showAlert("Errore", "Inserisci un numero valido di posti a sedere!");
                return;
            }
        }

        System.out.println("Posti selezionati: " + posti); // Debug

        // Creazione prenotazione
        PrenotazioneBean prenotazione = new PrenotazioneBean(
                null,                                // ID lo assegna il DAO
                calendar.getValue(),                 // data prenotazione
                calendar.getValue().plusDays(1),     // data scadenza
                orascelta.getText(),                 // ora prenotazione
                usernameUtente,                      // username utente
                ristoranteScelto.getNome(),          // nome ristorante
                posti                                 // posti a sedere
        );

        prenotazioneController.salvaPrenotazione(prenotazione);

        showAlert("Successo", "Prenotazione confermata per " +
                ristoranteScelto.getNome() + " da parte di " + usernameUtente +
                " alle ore " + orascelta.getText() +
                " per " + posti + " persone.");
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
