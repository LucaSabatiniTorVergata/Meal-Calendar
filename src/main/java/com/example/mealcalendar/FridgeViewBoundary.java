package com.example.mealcalendar;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class FridgeViewBoundary {

    @FXML
    private Button home;
    @FXML
    private TextField txtIngrediente;
    @FXML
    private TextField txtQuantita;
    @FXML
    private ListView<String> listaInventario;

    private FrigoriferoController frigoriferoController;
    private IngredienteValidoSet ingredienteValidoSet = IngredienteValidoSet.getInstance();
    Logger logger = Logger.getLogger(getClass().getName());

    // Nuova variabile per la modalità di persistenza
    private boolean usePersistence;

    public FridgeViewBoundary(boolean usePersistence) throws IOException {
        this.usePersistence = usePersistence;
        this.frigoriferoController = new FrigoriferoController(usePersistence);
    }

    public FridgeViewBoundary() throws IOException {
        this(false); // Imposta un valore di default per usePersistence
    }


    @FXML
    public void initialize() {
        if (frigoriferoController == null) {
            try {
                frigoriferoController = new FrigoriferoController(usePersistence);
            } catch (IOException e) {
                logger.severe("Errore nell'inizializzazione del controller: " + e.getMessage());
            }
        }
        aggiornaInventario();
    }


    @FXML
    private void rimuoviIngrediente(ActionEvent event) throws IOException {
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
            frigoriferoController.rimuoviIngrediente(nomeIngrediente, quantita);
            aggiornaInventario();
        } catch (NumberFormatException e) {
            logger.info("Errore: Inserisci un numero valido per la quantità!");
        }

        txtIngrediente.clear();
        txtQuantita.clear();
    }

    @FXML
    private void aggiungiIngrediente(ActionEvent event) throws IOException {
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
            frigoriferoController.aggiungiIngrediente(nomeIngrediente, quantita);
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
    private void backview(ActionEvent event) throws IOException {
        Stage stage = (Stage) home.getScene().getWindow();
        GraphicController.cambiascena(stage, "usermenu-view.fxml");
    }
}
