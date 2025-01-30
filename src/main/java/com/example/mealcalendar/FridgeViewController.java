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

    public FridgeViewController() {
        frigoriferoController = new FrigoriferoController();  // Crea un controller per il frigorifero
    }
    @FXML
    private void aggiungiIngrediente(ActionEvent event) {
        // Prendi il nome dell'ingrediente dal campo di testo
        String nomeIngrediente = txtIngrediente.getText();

        // Aggiungi l'ingrediente al frigorifero tramite il controller
        frigoriferoController.aggiungiIngrediente(nomeIngrediente);

        // Pulisci il campo di testo
        txtIngrediente.clear();

        // Stampa l'inventario nella console ogni volta che un ingrediente viene aggiunto
        frigoriferoController.stampaInventario();
    }

    private void aggiornaInventario() {
        // Pulisce la lista nella UI
        listaInventario.getItems().clear();

        // Ottieni gli ingredienti dal frigorifero e aggiungili alla ListView
        for (Ingrediente ingrediente : frigoriferoController.getInventario()) {
            listaInventario.getItems().add(ingrediente.getNome());
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
