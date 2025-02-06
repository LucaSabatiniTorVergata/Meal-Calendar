package com.example.mealcalendar;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.*;

public class LoginViewBoundary {

        @FXML
        private Button guest;
        @FXML
        private Button indietro;
        @FXML
        private Button login;

        @FXML
        private TextField user;
        @FXML
        private PasswordField pass;
        @FXML
        private Label messagelabel;




    @FXML
        private void indietroview(ActionEvent event) throws IOException {

            Stage stage = (Stage) indietro.getScene().getWindow();
            GraphicController.cambiascena(stage, "hello-view.fxml");

        }
        @FXML
        private void logIn(ActionEvent event) throws IOException {

            String username = user.getText();
            String password = pass.getText();

            if (username.isEmpty() || password.isEmpty()) {
                messagelabel.setText("❌Inserisci username e password!");
                return;
            }

            LoginBean userLoginBean = new LoginBean(username, password);
            LoginController controller = new LoginController();
            boolean success = controller.login(userLoginBean);

            if (success) {
                SessionManagerSLT.getInstance().setLoggedInUsername(userLoginBean.getUsername());
                Stage stage = (Stage) login.getScene().getWindow();
                GraphicController.cambiascena(stage, "usermenu-view.fxml");
            } else {
                messagelabel.setText("❌ Credenziali errate.");
            }
        }
        @FXML
        private void here(ActionEvent event) throws IOException {

            Stage stage = (Stage) guest.getScene().getWindow();
            GraphicController.cambiascena(stage, "guestmenu-view.fxml");


        }
    }
