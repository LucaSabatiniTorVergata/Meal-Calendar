package com.example.mealcalendar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class NutrorUserViewController {

    @FXML
    private Button btnNutrizionista;

    @FXML
    private Button btnUser;

    @FXML
    private Button btnLogin;


    @FXML
    private void goRegisterU(ActionEvent event) {
        SessionManagerSLT.getInstance().setRuolo("utente");
        Stage stage = (Stage) btnUser.getScene().getWindow();
        GraphicController.cambiascena(stage, "register-view.fxml");
    }

    @FXML
    private void goRegisterN(ActionEvent event) {
        SessionManagerSLT.getInstance().setRuolo("nutrizionista");
        Stage stage = (Stage) btnNutrizionista.getScene().getWindow();
        GraphicController.cambiascena(stage, "register-view.fxml");
    }


    @FXML
    private void goLogin(ActionEvent event) {
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        GraphicController.cambiascena(stage, "login-view.fxml");
    }

}
