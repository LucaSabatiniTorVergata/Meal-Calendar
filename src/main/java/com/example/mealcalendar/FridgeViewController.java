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

    private FrigoriferoController controller = new FrigoriferoController();

    @FXML
    private void aggiungiIngrediente() {
        String nomeIngrediente = txtIngrediente.getText().trim();
        String quantitaStr = txtQuantita.getText().trim();

        if (!nomeIngrediente.isEmpty() && !quantitaStr.isEmpty()) {
            try {
                int quantita = Integer.parseInt(quantitaStr);
                if (quantita > 0) {
                    for (int i = 0; i < quantita; i++) {
                        controller.gestisciIngrediente(nomeIngrediente);
                    }
                    aggiornaLista();
                    txtIngrediente.clear();
                    txtQuantita.clear();
                }
            } catch (NumberFormatException e) {
                System.out.println("Inserire un numero valido per la quantità.");
            }
        }
    }

    private void aggiornaLista() {
        listaInventario.getItems().clear();
        for (Map.Entry<String, Integer> entry : controller.getInventario().entrySet()) {
            listaInventario.getItems().add(entry.getKey() + ": " + entry.getValue());
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
