package com.example.mealcalendar.login;

import java.io.IOException;

import com.example.mealcalendar.GraphicController;
import com.example.mealcalendar.SessionManagerSLT;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.*;

public class HelloViewController {



    @FXML
    private Button quibutton;
    @FXML
    private Button register;
    @FXML
    private Button login;
    @FXML
    private Button backbutton;

    @FXML
    private TextField usernamefield;
    @FXML
    private TextField emailfield;
    @FXML
    private PasswordField passWordBox;

    @FXML
    private Label messageLabel;



    @FXML
    private void goback(ActionEvent event)  {
        Stage stage = (Stage) backbutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "hello-view.fxml");
    }

    @FXML
    private void guestmenuview(ActionEvent event)  {
        Stage stage = (Stage) quibutton.getScene().getWindow();
        GraphicController.cambiascena(stage, "guestmenu-view.fxml");
    }

    @FXML
    private void loginview(ActionEvent event) {
        Stage stage = (Stage) login.getScene().getWindow();
        GraphicController.cambiascena(stage,"login-view.fxml");
    }

    // Metodo per cambiare modalità su File System


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

        String ruolo=SessionManagerSLT.getInstance().getRuolo();
        RegisterController controller = new RegisterController();

        UserBean userRegisterBean = new UserBean(username, email, password,ruolo);
        boolean results = controller.register(userRegisterBean);

        if (results) {
            messageLabel.setText("✅ Registrazione completata!");
            Stage stage = (Stage) register.getScene().getWindow();
            GraphicController.cambiascena(stage, "checkout-view.fxml");
        } else {
            messageLabel.setText("❌ Username già esistente.");
        }
    }



}


