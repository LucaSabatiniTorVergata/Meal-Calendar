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


public class FridgeViewController {

    @FXML
    private TextField txtIngrediente;
    @FXML
    private TextField txtQuantita;
    @FXML
    private ListView<String> listaInventario;

    private FrigoriferoController frigoriferoController;
    private Frigorifero frigorifero = FrigoriferoController.getFrigorifero();

    public FridgeViewController() {
        frigoriferoController = new FrigoriferoController();  // Crea un controller per il frigorifero
    }


    @FXML
    public void initialize() {
        aggiornaInventario(); // 🔥 Carica l'inventario esistente quando la schermata si apre
    }



    @FXML
    private void aggiungiIngrediente(ActionEvent event) {
        String nomeIngrediente = txtIngrediente.getText().trim();
        String quantitaText = txtQuantita.getText().trim();

        // Controlliamo se entrambi i campi sono valorizzati
        if (nomeIngrediente.isEmpty() || quantitaText.isEmpty()) {
            System.out.println("Inserisci nome e quantità dell'ingrediente!");
            return;
        }

        try {
            int quantita = Integer.parseInt(quantitaText); // Convertiamo la quantità

            // Passiamo nome + quantità al frigo!
            frigorifero.aggiungiIngrediente(nomeIngrediente, quantita);

            mostraInventario(); // 🔥 Aggiorniamo la ListView

        } catch (NumberFormatException e) {
            System.out.println("Errore: Inserisci un numero valido per la quantità!");
        }

        // Puliamo i campi dopo l'aggiunta
        txtIngrediente.clear();
        txtQuantita.clear();
    }


    private void aggiornaInventario() {
        // Pulisce la lista nella UI
        listaInventario.getItems().clear();

        // Ottieni gli ingredienti dal frigorifero e aggiungili alla ListView
        for (Map.Entry<String, Integer> entry : frigorifero.getInventario().entrySet()) {
            listaInventario.getItems().add(entry.getKey() + " - Quantità: " + entry.getValue());
        }
    }

    @FXML
    private void mostraInventario() {
        listaInventario.getItems().clear();  // Puliamo la ListView
        for (Map.Entry<String, Integer> entry : frigorifero.getInventario().entrySet()) {
            listaInventario.getItems().add(entry.getKey() + " - Quantità: " + entry.getValue());
        }
    }

    @FXML
    private void backview(ActionEvent event) throws IOException {
        Parent NuovaSchermata = FXMLLoader.load(getClass().getResource("usermenu-view.fxml"));
        Scene NuovaScena = new Scene(NuovaSchermata);
        Stage Finestra = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Finestra.setScene(NuovaScena);
        Finestra.show();
    }
}
