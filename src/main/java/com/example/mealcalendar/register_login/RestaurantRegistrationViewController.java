package com.example.mealcalendar.register_login;

import com.example.mealcalendar.GraphicController;
import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.model.TipologiaRistorante;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RestaurantRegistrationViewController {

    @FXML private TextField usernamefield;
    @FXML private TextField emailfield;
    @FXML private PasswordField passWordBox;
    @FXML private Label messageLabel;
    @FXML private Button login;
    @FXML private Button backbutton;
    @FXML private SplitMenuButton tipologia;

    private TipologiaRistorante tipologiaSelezionata; // salva la scelta

    @FXML
    public void initialize() {
        // Popola lo SplitMenuButton con tutte le tipologie
        for (TipologiaRistorante t : TipologiaRistorante.values()) {
            MenuItem item = new MenuItem(t.name());
            item.setOnAction(e -> {
                tipologia.setText(t.name()); // mostra la scelta nel bottone
                tipologiaSelezionata = t;    // salva la scelta
            });
            tipologia.getItems().add(item);
        }
    }

    @FXML
    public void goback() {
        Stage stage = (Stage) backbutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "/com/example/mealcalendar/hello-View.fxml");
    }

    @FXML
    public void register() {
        String username = usernamefield.getText();
        String email = emailfield.getText();
        String password = passWordBox.getText();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Compila tutti i campi.");
            return;
        }

        if ("restaurant".equalsIgnoreCase(SessionManagerSLT.getInstance().getRuolo()) && tipologiaSelezionata == null) {
            messageLabel.setText("Seleziona una tipologia valida!");
            return;
        }

        UserBeanA bean = new UserBeanA(
                username,
                email,
                password,
                SessionManagerSLT.getInstance().getRuolo()
        );

        RegistrationController regController = new RegistrationController();

        try {
            // Se è un ristorante, usa anche la tipologia
            if (tipologiaSelezionata != null) {
                regController.register(bean, tipologiaSelezionata);
            } else {
                regController.register(bean);
            }
            messageLabel.setText("Registrazione completata!");
        } catch (Exception e) {
            messageLabel.setText("Errore nella registrazione.");
            e.printStackTrace();
        }
    }

    @FXML
    public void gologin() {
        Stage stage = (Stage) login.getScene().getWindow();
        GraphicController.cambiascena(stage, "/com/example/mealcalendar/login-view.fxml");
    }
}
