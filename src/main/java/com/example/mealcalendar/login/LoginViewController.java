package com.example.mealcalendar.login;

import java.io.IOException;

import com.example.mealcalendar.GraphicController;
import com.example.mealcalendar.SessionManagerSLT;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.*;

public class LoginViewController {

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
        private void indietroview(ActionEvent event)  {

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
        private void here(ActionEvent event) {
            Stage stage = (Stage) guest.getScene().getWindow();
            GraphicController.cambiascena(stage, "guestmenu-view.fxml");
         }
    }
