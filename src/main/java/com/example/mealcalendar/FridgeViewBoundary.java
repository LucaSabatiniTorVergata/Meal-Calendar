package com.example.mealcalendar;

import java.io.IOException;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


public class FridgeViewBoundary {

    @FXML
    private TextField txtIngrediente;
    @FXML
    private TextField txtQuantita;
    @FXML
    private ListView<String> listaInventario;

    private FrigoriferoController frigoriferoController = FrigoriferoController.getInstance();
    private IngredienteValidoSet ingredienteValidoSet = IngredienteValidoSet.getInstance();

    @FXML
    public void initialize() {
        aggiornaInventario();
    }

    @FXML
    private void aggiungiIngrediente(ActionEvent event) {
        String nomeIngrediente = txtIngrediente.getText().trim();
        String quantitaText = txtQuantita.getText().trim();

        // Controllo se l'ingrediente e la quantità sono stati inseriti
        if (nomeIngrediente.isEmpty() || quantitaText.isEmpty()) {
            System.out.println("Inserisci nome e quantità dell'ingrediente!");
            return;
        }

        // Verifica se l'ingrediente è valido (presente nell'elenco predefinito)
        if (!ingredienteValidoSet.isIngredienteValido(nomeIngrediente)) {
            System.out.println("Errore: Inserisci un ingrediente valido!");
            return;
        }

        try {
            int quantita = Integer.parseInt(quantitaText);

            // Aggiungi l'ingrediente alla lista tramite il controller
            frigoriferoController.aggiungiIngrediente(nomeIngrediente, quantita);

            aggiornaInventario(); // Aggiorniamo la lista dell'inventario
        } catch (NumberFormatException e) {
            System.out.println("Errore: Inserisci un numero valido per la quantità!");
        }

        // Puliamo i campi dopo l'aggiunta
        txtIngrediente.clear();
        txtQuantita.clear();
    }

    private void aggiornaInventario() {
        listaInventario.getItems().clear();

        for (Map.Entry<String, Integer> entry : frigoriferoController.getInventario().entrySet()) {
            listaInventario.getItems().add(entry.getKey() + " - Quantità: " + entry.getValue());
        }
    }


    // Aggiungi altre azioni come il back a seconda delle necessità



    @FXML
    private void backview(ActionEvent event) throws IOException {
        Parent NuovaSchermata = FXMLLoader.load(getClass().getResource("usermenu-view.fxml"));
        Scene NuovaScena = new Scene(NuovaSchermata);
        Stage Finestra = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Finestra.setScene(NuovaScena);
        Finestra.show();
    }
}
