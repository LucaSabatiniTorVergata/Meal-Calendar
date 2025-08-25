package com.example.mealcalendar.view_controller;

import com.example.mealcalendar.GraphicController;
import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.bean.PrenotazioneBean;
import com.example.mealcalendar.controller_applicativo.PrenotazioneController;
import com.example.mealcalendar.exception.PrenotazioneException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.List;

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
    private ListView<PrenotazioneBean> prenotazioniListView;

    @FXML
    private Label welcomelabel;

    private final PrenotazioneController prenotazioneController = new PrenotazioneController();

    @FXML
    public void initialize() throws PrenotazioneException {
        caricaPrenotazioniUtente();

        // Customizzazione grafica delle celle della ListView
        prenotazioniListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(PrenotazioneBean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText("ID: " + item.getId() +
                            " | Ristorante: " + item.getNomeRistorante() +
                            " | Data: " + item.getDataPrenotazione() +
                            " | Ora: " + item.getOraPrenotazione() +
                            " | Posti: " + item.getPostiASedere());

                    // Mostriamo in rosso le prenotazioni scadute
                    if (item.isScaduta()) {
                        setStyle("-fx-text-fill: red;");
                    } else {
                        setStyle(""); // stile di default
                    }
                }
            }
        });
    }

    private void caricaPrenotazioniUtente() throws PrenotazioneException {
        loadingIndicator.setVisible(true);

        String username = SessionManagerSLT.getInstance().getLoggedInUsername();
        lableemail.setText("Utente: " + username);

        // tutte le prenotazioni già marcate come scadute dal controller
        List<PrenotazioneBean> tutte = prenotazioneController.getPrenotazioni();

        // filtriamo solo quelle dell'utente corrente
        List<PrenotazioneBean> filtrate = tutte.stream()
                .filter(p -> p.getUsernameUtente().equals(username))
                .toList();

        prenotazioniListView.getItems().setAll(filtrate);
        loadingIndicator.setVisible(false);
    }

    @FXML
    void handleclick(MouseEvent event) {
        PrenotazioneBean selezionata = prenotazioniListView.getSelectionModel().getSelectedItem();
        if (selezionata != null) {
            System.out.println("Hai selezionato la prenotazione con ID: " + selezionata.getId() +
                    " | Posti: " + selezionata.getPostiASedere());
        }
    }

    @FXML
    void loadHomePageUser(ActionEvent event) {
        Stage stage = (Stage) homebutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "menu-view.fxml");
    }

    @FXML
    void eliminaPrenotazioneSelezionata(ActionEvent event) throws PrenotazioneException {
        PrenotazioneBean selezionata = prenotazioniListView.getSelectionModel().getSelectedItem();
        if (selezionata == null) {
            showAlert("Errore", "Seleziona una prenotazione da eliminare!");
            return;
        }

        boolean conferma = confermaDialog("Conferma eliminazione",
                "Sei sicuro di voler eliminare la prenotazione con ID: " + selezionata.getId() + "?");

        if (!conferma) return;

        boolean eliminata = prenotazioneController.eliminaPrenotazione(selezionata);
        if (eliminata) {
            prenotazioniListView.getItems().remove(selezionata);
            showAlert("Successo", "Prenotazione eliminata correttamente!");
        } else {
            showAlert("Errore", "Non è stato possibile eliminare la prenotazione.");
        }
    }

    private boolean confermaDialog(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);

        return alert.showAndWait().filter(response -> response == ButtonType.OK).isPresent();
    }

    private void showAlert(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}
