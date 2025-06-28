package com.example.mealcalendar.fillfridge;

import java.util.Map;
import java.util.logging.Logger;

import com.example.mealcalendar.GraphicController;
import com.example.mealcalendar.SessionManagerSLT;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class FridgeViewController {

    @FXML
    private Label label;
    @FXML
    private Button home;
    @FXML
    private Button btnEnablePersistence;
    @FXML
    private Button btnDisablePersistence;
    @FXML
    private TextField txtIngrediente;
    @FXML
    private TextField txtQuantita;
    @FXML
    private ListView<String> listaInventario;

    private FrigoriferoController frigoriferoController;
    private IngredienteValidoSet ingredienteValidoSet = IngredienteValidoSet.getInstance();
    Logger logger = Logger.getLogger(getClass().getName());

    @FXML
    public void initialize() {
        label.setText("Hi, " + SessionManagerSLT.getInstance().getLoggedInUsername() + "!");
        listaInventario.getItems().clear();
        if (frigoriferoController == null) {
            frigoriferoController = new FrigoriferoController(SessionManagerSLT.getInstance().getDemo());
        }
        aggiornaInventario();
    }

    @FXML
    private void aggiungiIngrediente(ActionEvent event) {
        gestisciIngrediente(true);
    }

    @FXML
    private void rimuoviIngrediente(ActionEvent event) {
        gestisciIngrediente(false);
    }

    private void gestisciIngrediente(boolean aggiungi) {
        String nomeIngrediente = txtIngrediente.getText().trim();
        String quantitaText = txtQuantita.getText().trim();

        if (nomeIngrediente.isEmpty() || quantitaText.isEmpty()) {
            logger.info("Inserisci nome e quantità dell'ingrediente!");
            return;
        }

        if (!ingredienteValidoSet.isIngredienteValido(nomeIngrediente)) {
            logger.info("Errore: Inserisci un ingrediente valido!");
            return;
        }

        try {
            int quantita = Integer.parseInt(quantitaText);
            if (aggiungi) {
                frigoriferoController.aggiungiIngrediente(nomeIngrediente, quantita);
            } else {
                frigoriferoController.rimuoviIngrediente(nomeIngrediente, quantita);
            }
            aggiornaInventario();
        } catch (NumberFormatException e) {
            logger.info("Errore: Inserisci un numero valido per la quantità!");
        }

        txtIngrediente.clear();
        txtQuantita.clear();
    }

    private void aggiornaInventario() {
        listaInventario.getItems().clear();
        for (Map.Entry<String, Integer> entry : frigoriferoController.getInventario().entrySet()) {
            listaInventario.getItems().add(entry.getKey() + " - Quantità: " + entry.getValue());
        }
    }

    @FXML
    private void backview(ActionEvent event) {
        Stage stage = (Stage) home.getScene().getWindow();
        GraphicController.cambiascena(stage, "usermenu-view.fxml");
    }
}
