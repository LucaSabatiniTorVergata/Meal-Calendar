package com.example.mealcalendar;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.*;

public class HelloViewBoudary {

    @FXML
    private Button quibutton;
    @FXML
    private Button register;
    @FXML
    private Button login;

    @FXML
    private TextField usernamefield;
    @FXML
    private TextField emailfield;
    @FXML
    private PasswordField passWordBox;
    @FXML
    private Label messageLabel;



    @FXML
    private void guestmenuview(ActionEvent event) throws IOException  {
        Stage stage = (Stage) quibutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "guestmenu-view.fxml");
    }

    @FXML
    private void loginview(ActionEvent event) throws IOException {
        Stage stage = (Stage) login.getScene().getWindow();
        GraphicController.cambiascena(stage, "login-view.fxml");
    }

    // Metodo per cambiare modalità su File System
    @FXML
    private void useFileSystem(ActionEvent event) {
        UserDaoFactory.setUseDatabase(false);
        UserDaoFactory.setUseDemo(false);// Imposta l'uso del File System
        messageLabel.setText("🔹 Utilizzando il File System per i dati utenti.");
    }

    // Metodo per cambiare modalità su Database
    @FXML
    private void useDatabase(ActionEvent event) {
        UserDaoFactory.setUseDatabase(true);
        UserDaoFactory.setUseDemo(false);// Imposta l'uso del Database
        messageLabel.setText("🔹 Utilizzando il Database per i dati utenti.");
    }

    // Metodo per registrare un nuovo utente
    @FXML
    private void register(ActionEvent event) throws IOException {
        String username = usernamefield.getText();
        String email = emailfield.getText();
        String password = passWordBox.getText();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            messageLabel.setText("❌ Completa tutti i campi!");
            return;
        }

        // Crea il DAO tramite la Factory (già configurato con FS o DB)
        UserDaoInterface userDao = UserDaoFactory.createUserDao();

        // Passiamo il DAO al RegisterController
        RegisterController controller = new RegisterController(userDao);

        UserBean userRegisterBean = new UserBean(username, email, password);
        boolean results = controller.register(userRegisterBean);

        if (results) {
            messageLabel.setText("✅ Registrazione completata!");
            Stage stage = (Stage) register.getScene().getWindow();
            GraphicController.cambiascena(stage, "checkout-view.fxml");
        } else {
            messageLabel.setText("❌ Username già esistente.");
        }
    }

    @FXML
    public void useRam(ActionEvent event) {
        UserDaoFactory.setUseDemo(true); // Imposta l'uso del File System
        messageLabel.setText("🔹 Utilizzando senza Persistenza...");
    }


}
