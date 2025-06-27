package com.example.mealcalendar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class NutrorUserViewController {

    @FXML
    private Button btnNutrizionista;

    @FXML
    private Button btnUser;

    @FXML
    private Button btnLogin;

    @FXML
    private Button dbbutton;

    @FXML
    private Button rambutton;

    @FXML
    private Button fsbutton;

    @FXML
    private Label messageLabel;


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

    @FXML
    private void useFileSystem(ActionEvent event) {
        SessionManagerSLT.getInstance().setRam(false); // Nasconde il bottone ram
        SessionManagerSLT.getInstance().setDB(false); // Nasconde il bottone db
        SessionManagerSLT.getInstance().setFs(true);  // Nasconde il bottone fs
        updateButtonVisibility();
        SessionManagerSLT.getInstance().setFSDataBase(false);
        SessionManagerSLT.getInstance().setDemo(false);
        messageLabel.setText("🔹 Utilizzando il File System per i dati utenti.");
    }

    @FXML
    public void useRam(ActionEvent event) {
        SessionManagerSLT.getInstance().setRam(true); // Nasconde il bottone ram
        SessionManagerSLT.getInstance().setDB(false); // Nasconde il bottone db
        SessionManagerSLT.getInstance().setFs(false); // Nasconde il bottone fs
        updateButtonVisibility();
        SessionManagerSLT.getInstance().setFSDataBase(false);
        SessionManagerSLT.getInstance().setDemo(true);
        messageLabel.setText("🔹 Utilizzando la demo per i dati utenti.");
    }

    @FXML
    private void useDatabase(ActionEvent event) {
        SessionManagerSLT.getInstance().setRam(false); // Nasconde il bottone ram
        SessionManagerSLT.getInstance().setDB(true); // Nasconde il bottone db
        SessionManagerSLT.getInstance().setFs(false);  // Nasconde il bottone fs
        updateButtonVisibility();
        SessionManagerSLT.getInstance().setFSDataBase(true);
        SessionManagerSLT.getInstance().setDemo(false);
        messageLabel.setText("🔹 Utilizzando il Database per i dati utenti.");
    }

    @FXML
    public void initialize(){

        updateButtonVisibility();

    }

    private void updateButtonVisibility() {
        rambutton.setVisible(SessionManagerSLT.getInstance().getRam());
        dbbutton.setVisible(SessionManagerSLT.getInstance().getDB());
        fsbutton.setVisible(SessionManagerSLT.getInstance().getFs());
    }


}
