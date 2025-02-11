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

    private boolean usePersistence;

    // Costruttore che inizializza la modalità di persistenza
    public FridgeViewBoundary(boolean usePersistence) throws IOException {
        this.usePersistence = usePersistence;
        this.frigoriferoController = new FrigoriferoController(usePersistence);
    }

    // Costruttore di default senza persistenza abilitata
    public FridgeViewBoundary() throws IOException {
        this(false);
    }

    // Metodo chiamato al caricamento della view
    @FXML
    public void initialize() {
        aggiornaInventario();
    }

    // Aggiungi ingrediente all'inventario
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

        // Pulisce i campi di input
        txtIngrediente.clear();
        txtQuantita.clear();
    }

    // Rimuovi ingrediente dall'inventario
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

        // Pulisce i campi di input
        txtIngrediente.clear();
        txtQuantita.clear();
    }

    // Metodo per aggiornare la visualizzazione dell'inventario
    private void aggiornaInventario() {
        listaInventario.getItems().clear();
        for (Map.Entry<String, Integer> entry : frigoriferoController.getInventario().entrySet()) {
            listaInventario.getItems().add(entry.getKey() + " - Quantità: " + entry.getValue());
        }
    }

    // Gestisce il cambio di scena
    @FXML
    private void backview(ActionEvent event) throws IOException {
        Stage stage = (Stage) home.getScene().getWindow();
        GraphicController.cambiascena(stage, "usermenu-view.fxml");
    }

    // Abilita la persistenza
    @FXML
    private void enablePersistence(ActionEvent event) throws IOException {
        if (!usePersistence) {
            this.usePersistence = true;
            frigoriferoController.setUsePersistence(usePersistence);
            aggiornaInventario();
            logger.info("Persistenza abilitata.");
        }
    }

    // Disabilita la persistenza
    @FXML
    private void disablePersistence(ActionEvent event) throws IOException {
        if (usePersistence) {
            this.usePersistence = false;
            frigoriferoController.setUsePersistence(usePersistence);
            aggiornaInventario();
            logger.info("Persistenza disabilitata.");
        }
    }
}
