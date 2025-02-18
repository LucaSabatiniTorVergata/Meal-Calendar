package com.example.mealcalendar;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class FridgeViewBoundary {

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

    // Variabile per la modalità di persistenza

    @FXML
    public void initialize() {

        label.setText("Hi, " + SessionManagerSLT.getInstance().getLoggedInUsername() + "!");
        listaInventario.getItems().clear();
        if (frigoriferoController == null) {
            try {
                frigoriferoController = new FrigoriferoController(SessionManagerSLT.getInstance().getDemo());
            } catch (IOException e) {
                logger.severe("Errore nell'inizializzazione del controller: " + e.getMessage());
            }
        }
        aggiornaInventario();
    }

    // Aggiunge un ingrediente all'inventario
    @FXML
    private void aggiungiIngrediente(ActionEvent event) throws IOException {
        String nomeIngrediente = txtIngrediente.getText().trim();
        String quantitaText = txtQuantita.getText().trim();

        // Controlla se i campi sono vuoti
        if (nomeIngrediente.isEmpty() || quantitaText.isEmpty()) {
            logger.info("Inserisci nome e quantità dell'ingrediente!");
            return;
        }

        // Verifica se l'ingrediente è valido
        if (!ingredienteValidoSet.isIngredienteValido(nomeIngrediente)) {
            logger.info("Errore: Inserisci un ingrediente valido!");
            return;
        }

        try {
            int quantita = Integer.parseInt(quantitaText);
            frigoriferoController.aggiungiIngrediente(nomeIngrediente, quantita);
            aggiornaInventario();
        } catch (NumberFormatException e) {
            logger.info("Errore: Inserisci un numero valido per la quantità!");
        }

        // Pulisce i campi di input
        txtIngrediente.clear();
        txtQuantita.clear();
    }

    // Rimuove un ingrediente dall'inventario
    @FXML
    private void rimuoviIngrediente(ActionEvent event) throws IOException {
        String nomeIngrediente = txtIngrediente.getText().trim();
        String quantitaText = txtQuantita.getText().trim();

        // Controlla se i campi sono vuoti
        if (nomeIngrediente.isEmpty() || quantitaText.isEmpty()) {
            logger.info("Inserisci nome e quantità dell'ingrediente!");
            return;
        }

        // Verifica se l'ingrediente è valido
        if (!ingredienteValidoSet.isIngredienteValido(nomeIngrediente)) {
            logger.info("Errore: Inserisci un ingrediente valido!");
            return;
        }

        try {
            int quantita = Integer.parseInt(quantitaText);
            frigoriferoController.rimuoviIngrediente(nomeIngrediente, quantita);
            aggiornaInventario();
        } catch (NumberFormatException e) {
            logger.info("Errore: Inserisci un numero valido per la quantità!");
        }

        // Pulisce i campi di input
        txtIngrediente.clear();
        txtQuantita.clear();
    }

    // Metodo per aggiornare la visualizzazione dell'inventario nella ListView
    private void aggiornaInventario() {
        listaInventario.getItems().clear();
        for (Map.Entry<String, Integer> entry : frigoriferoController.getInventario().entrySet()) {
            listaInventario.getItems().add(entry.getKey() + " - Quantità: " + entry.getValue());
        }
    }

    @FXML
    private void backview(ActionEvent event) throws IOException {
        Stage stage = (Stage) home.getScene().getWindow();
        GraphicController.cambiascena(stage, "usermenu-view.fxml");
    }

}
