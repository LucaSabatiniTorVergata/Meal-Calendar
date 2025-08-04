package com.example.mealcalendar.register_login;

import com.example.mealcalendar.GraphicController;
import com.example.mealcalendar.SessionManagerSLT;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HelloViewController {

    @FXML
    private Button btnUser;
    @FXML private Button btnNutrizionista;
    @FXML private Button btnLogin;
    @FXML private Button btnRest1;
    @FXML private Button dbbutton;
    @FXML private Button fsbutton;
    @FXML private Button rambutton;
    @FXML private Label messageLabel;

    @FXML
    public void useDatabase() {


        SessionManagerSLT.getInstance().setDemo(false);
        SessionManagerSLT.getInstance().setFSDataBase(false);
        messageLabel.setText("Modalità: Database attiva");
    }

    @FXML
    public void useFileSystem() {

        SessionManagerSLT.getInstance().setDemo(false);
        SessionManagerSLT.getInstance().setFSDataBase(true);
        messageLabel.setText("Modalità: File System attiva");
    }

    @FXML
    public void useRam() {


        SessionManagerSLT.getInstance().setDemo(true);
        SessionManagerSLT.getInstance().setFSDataBase(false);
        messageLabel.setText("Modalità: RAM attiva");

    }

    @FXML
    public void goRegisterU() {

        SessionManagerSLT.getInstance().setRuolo("user");
        Stage stage = (Stage) btnUser.getScene().getWindow();
        GraphicController.cambiascena(stage, "/com/example/mealcalendar/register-view.fxml");
    }

    @FXML
    public void goRegisterN() {

        SessionManagerSLT.getInstance().setRuolo("nutritionist");
        Stage stage = (Stage) btnNutrizionista.getScene().getWindow();
        GraphicController.cambiascena(stage, "/com/example/mealcalendar/register-view.fxml");

    }

    @FXML
    public void goLogin() {

        Stage stage = (Stage) btnLogin.getScene().getWindow();
        GraphicController.cambiascena(stage, "/com/example/mealcalendar/login-view.fxml");

    }

    @FXML
    public void goRestLogin() {}

}
