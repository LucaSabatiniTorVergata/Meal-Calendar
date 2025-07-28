package com.example.mealcalendar.register_login;

import com.example.mealcalendar.GraphicController;
import com.example.mealcalendar.SessionManagerSLT;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class RegistrationViewController {

    @FXML
    private TextField usernamefield;
    @FXML private TextField emailfield;
    @FXML private PasswordField passWordBox;
    @FXML private Label messageLabel;
    @FXML private Button login;
    @FXML private Button backbutton;

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

        UserBean bean = new UserBean(username,email,password,SessionManagerSLT.getInstance().getRuolo());


        RegistrationController regController = new RegistrationController();

        try {
            regController.register(bean);
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
