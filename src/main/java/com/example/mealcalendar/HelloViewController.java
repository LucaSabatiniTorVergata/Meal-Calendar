package com.example.mealcalendar;

import java.io.IOException;
import javafx.fxml.FXML;
import org.mindrot.jbcrypt.BCrypt;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HelloViewController {

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

    @FXML
    private void register(ActionEvent event) throws IOException {

        String username = usernamefield.getText();
        String email = emailfield.getText();
        String password = passWordBox.getText();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            messageLabel.setText(" Completa tutti i campi!");
            return;
        }
        UserBean userRegisterBean = new UserBean(username, email, password);
        RegisterController controller=new RegisterController();
        boolean results = controller.register(userRegisterBean);

        if (results){
            messageLabel.setText("✅ Registrazione completata!");
            Stage stage = (Stage) register.getScene().getWindow();
            GraphicController.cambiascena(stage, "checkout-view.fxml");
        }else {
            messageLabel.setText("❌ Username già esistente.");
        }

    }

}





