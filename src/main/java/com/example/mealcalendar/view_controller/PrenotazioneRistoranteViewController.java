package com.example.mealcalendar.view_controller;

import com.example.mealcalendar.GraphicController;
import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.bean.PrenotazioneBean;
import com.example.mealcalendar.bean.RistoranteBean;
import com.example.mealcalendar.controller_applicativo.PrenotazioneController;
import com.example.mealcalendar.exception.PrenotazioneException;
import com.example.mealcalendar.model.TipologiaRistorante;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class PrenotazioneRistoranteViewController {

    private static final String ERROR_TITLE = "Errore"; // costante per il titolo degli alert di errore

    private final PrenotazioneController prenotazioneController = new PrenotazioneController();

    @FXML private Button backhome;
    @FXML private DatePicker calendar;
    @FXML private Button confirmButton;
    @FXML private Button ricarica;
    @FXML private SplitMenuButton tipoSelezionato;
    @FXML private ListView<RistoranteBean> listaRistoranti;
    @FXML private TextField orascelta;
    @FXML private TextField postiASedere;

    private TipologiaRistorante tipologiaSelezionata;

    @FXML
    public void initialize() {
        tipoSelezionato.getItems().clear();
        for (TipologiaRistorante t : TipologiaRistorante.values()) {
            MenuItem item = new MenuItem(t.name());
            item.setOnAction(e -> {
                tipoSelezionato.setText(t.name());
                tipologiaSelezionata = t;
            });
            tipoSelezionato.getItems().add(item);
        }

        calendar.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(java.time.LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isBefore(java.time.LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }
            }
        });
    }

    @FXML
    void confermaPrenotazione(ActionEvent event) {
        if (calendar.getValue() == null || orascelta.getText().isEmpty()) {
            showAlert(ERROR_TITLE, "Compila data e ora prima di confermare!");
            return;
        }

        RistoranteBean ristoranteScelto = listaRistoranti.getSelectionModel().getSelectedItem();
        if (ristoranteScelto == null) {
            showAlert(ERROR_TITLE, "Seleziona un ristorante dalla lista!");
            return;
        }

        String usernameUtente = SessionManagerSLT.getInstance().getLoggedInUsername();

        int posti = 1;
        if (postiASedere != null && !postiASedere.getText().isEmpty()) {
            try {
                posti = Integer.parseInt(postiASedere.getText());
            } catch (NumberFormatException e) {
                showAlert(ERROR_TITLE, "Inserisci un numero valido di posti a sedere!");
                return;
            }
        }

        PrenotazioneBean prenotazione = new PrenotazioneBean(
                null,
                calendar.getValue(),
                calendar.getValue().plusDays(1),
                orascelta.getText(),
                usernameUtente,
                ristoranteScelto.getNome(),
                posti
        );

        try {
            prenotazioneController.salvaPrenotazione(prenotazione);

            showAlert("Successo", "Prenotazione confermata per " +
                    ristoranteScelto.getNome() + " da parte di " + usernameUtente +
                    " alle ore " + orascelta.getText() +
                    " per " + posti + " persone.");

        } catch (PrenotazioneException e) {
            // Mostriamo un alert specifico se i posti non sono sufficienti
            showAlert("Errore prenotazione", e.getMessage());
        }
    }


    @FXML
    void homeview(ActionEvent event) {
        Stage stage = (Stage) backhome.getScene().getWindow();
        GraphicController.cambiascena(stage, "menu-view.fxml");
    }

    @FXML
    void caricaRistoranti(ActionEvent event) throws PrenotazioneException {
        listaRistoranti.getItems().clear();
        List<RistoranteBean> ristoranti = prenotazioneController.getRistoranti();

        if (tipologiaSelezionata != null) {
            ristoranti = ristoranti.stream()
                    .filter(r -> r.getTipologiaRistorante() == tipologiaSelezionata)
                    .toList();
        }

        listaRistoranti.getItems().addAll(ristoranti);

        listaRistoranti.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(RistoranteBean item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNome() + " - " + item.getIndirizzo());
            }
        });
    }

    private void showAlert(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}
