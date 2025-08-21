package com.example.mealcalendar.register_login;

import com.example.mealcalendar.GraphicController;
import com.example.mealcalendar.bean.RistoranteBean;
import com.example.mealcalendar.dao.RistoranteDao;
import com.example.mealcalendar.model.TipologiaRistorante;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RestaurantRegistrationViewController {

    @FXML private TextField nameField;
    @FXML private TextField addressField;
    @FXML private TextField postiField;
    @FXML private Label messageLabel;
    @FXML private Button backbutton;
    @FXML private SplitMenuButton tipologia;

    private TipologiaRistorante tipologiaSelezionata;

    private final RistoranteDao ristoranteDao = new RistoranteDao();

    @FXML
    public void initialize() {
        if (tipologia != null) {
            for (TipologiaRistorante t : TipologiaRistorante.values()) {
                MenuItem item = new MenuItem(t.name());
                item.setOnAction(e -> {
                    tipologia.setText(t.name());
                    tipologiaSelezionata = t;
                });
                tipologia.getItems().add(item);
            }
        }
    }

    @FXML
    public void goback() {
        Stage stage = (Stage) backbutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "/com/example/mealcalendar/hello-View.fxml");
    }

    @FXML
    public void gologin() {
        Stage stage = (Stage) backbutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "/com/example/mealcalendar/login-View.fxml");
    }

    @FXML
    public void register() {
        String nome = nameField.getText().trim();
        String indirizzo = addressField.getText().trim();
        String postiText = postiField.getText().trim();

        if (nome.isEmpty() || indirizzo.isEmpty() || postiText.isEmpty()) {
            messageLabel.setText("Compila tutti i campi obbligatori.");
            return;
        }

        int postiDisponibili;
        try {
            postiDisponibili = Integer.parseInt(postiText);
        } catch (NumberFormatException e) {
            messageLabel.setText("Numero posti non valido.");
            return;
        }

        if (tipologiaSelezionata == null) {
            tipologiaSelezionata = TipologiaRistorante.ONNIVORO; // default
        }

        RistoranteBean bean = new RistoranteBean(nome, indirizzo, postiDisponibili, tipologiaSelezionata);

        try {
            ristoranteDao.aggiungiRistorante(bean);
            messageLabel.setText("Registrazione completata!");
        } catch (Exception e) {
            messageLabel.setText("Errore nella registrazione.");
            e.printStackTrace();
        }
    }
}
